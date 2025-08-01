package com.libcentro.demo.services;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.libcentro.demo.config.AppConfig;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.model.ProductoFStock;
import com.libcentro.demo.model.Venta_Producto;
import com.libcentro.demo.model.dto.*;
import com.libcentro.demo.model.json.TicketData;
import com.libcentro.demo.utils.GeneradorTicket;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.libcentro.demo.model.Venta;
import com.libcentro.demo.repository.IventaRepository;
import com.libcentro.demo.services.interfaces.IventaService;
@Service
public class VentaService implements IventaService {

    private final IventaRepository ventaRepository;
    private final ProductoService productoService;
    private final JsonService<TicketData> ticketService;

    @Autowired
    public VentaService( IventaRepository ventaRepository, ProductoService productoService, JsonService<TicketData> ticketService ){
        this.ventaRepository = ventaRepository;
        this.productoService = productoService;
        this.ticketService = ticketService;
    }

    @Override
    public List<Venta> getAll() {
            return ventaRepository.findAll();
    }

    @Override
    @Transactional
    public Venta saveVenta(VentaDTO ventaDTO) {
        Venta venta = new Venta();
        venta.setFecha(ventaDTO.getFecha());
        venta.setEstado(ventaDTO.isEstado());
        venta.setTotal(ventaDTO.getTotal());
        venta.setId (generarVentaID());

        Venta savedVenta = ventaRepository.save(venta);

        savedVenta.setProductoFStocks(ventaDTO.getProducto_fstock().stream()
                .map(pfs -> {
                    ProductoFStock productoFStock = new ProductoFStock(pfs);
                    productoFStock.setVenta(savedVenta);
                    return productoFStock;
                })
                .collect(Collectors.toSet()));

        ventaDTO.getVenta_producto().forEach(vpd -> {
            Venta_Producto ventaproducto = new Venta_Producto(vpd);
            ventaproducto.setVenta(savedVenta);
            Producto producto = productoService.getProducto (vpd.getProducto().getCodigobarras());
                ventaproducto.setProducto(producto, vpd.getCantidad());
            savedVenta.getVenta_productos().add(ventaproducto);
        });

        return ventaRepository.save(savedVenta);
    }


    @Override
    @Transactional
    public void cambiarEstadoVenta ( String id) {
         ventaRepository.toggleEstadoById (id);
    }

    @Override
    public void vender( VentaDTO ventaDTO) throws RuntimeException {
        if(ventaDTO.getVenta_producto ().isEmpty () && ventaDTO.getProducto_fstock ().isEmpty ()) throw new RuntimeException("Seleccione productos para vender");

        GeneradorTicket generadorTicket=new GeneradorTicket();


        LocalDateTime dateTime = LocalDateTime.now();
        ventaDTO.setFecha(dateTime);

        Venta venta = saveVenta(ventaDTO);

        for(Venta_Producto ventaProducto : venta.getVenta_productos()){
            Producto productoVenta= ventaProducto.getProducto();
            ProductoDTO productoO = productoService.getProductoDTO (productoVenta.getCodigobarras ());

            CategoriaDTO categoriaDTO =productoO.getCategoria ();

            ProductoDTO productoDTO = new ProductoDTO (productoO.getCodigobarras (),
                    productoO.getNombre (),
                    categoriaDTO,
                    productoO.getCosto_compra (),
                    productoO.getPrecio_venta (),
                    productoO.getStock ());

            productoService.venderProducto (productoDTO,ventaProducto.getCantidad ());
        }

        TicketData ticketData;
        try {
            ticketData = ticketService.loadFromFile (AppConfig.ticket_path,TicketData.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException ("Error al obtener archivo de ticket");
        }
        String ticket = generadorTicket.generarTicket(venta,ticketData);
        generadorTicket.imprimirTicket();
    }

    @Override
    public PageDTO<VentaDTO> getByPage(String filter, int page, int page_size) {
        String filterT = (filter == null || filter.isEmpty()) ? null : "%" + filter.toLowerCase() + "%";

        Page<Venta> ventaPage = ventaRepository.findByIdStartingWithAndOrdered(
                PageRequest.of(page, page_size),
                filterT
        );

        List<VentaDTO> ventaDTOList = ventaPage.stream().map(this::ventaToDto).toList();

        return new PageDTO<>(ventaDTOList, ventaPage.getTotalPages());
    }

    @Override
    public VentaDTO getVentaById ( String value ){
        Optional<Venta> ventaOPT = ventaRepository.findById (value);
        VentaDTO ventaDTO;
        if(ventaOPT.isPresent ()){ ventaDTO = ventaToDto (ventaOPT.get ()); }
        else {throw new RuntimeException ("Venta no encontrada");}
        Venta venta = ventaOPT.get ();
        Set<Venta_ProductoDTO> ventaProductoDTO = venta.getVenta_productos ().stream().map (Venta_ProductoDTO::new).collect(Collectors.toSet());
        Set<ProductoFStockDTO> productoFStockDTOS = venta.getProductoFStocks ().stream ().map (ProductoFStockDTO::new).collect(Collectors.toSet());


        ventaDTO.setVenta_producto (ventaProductoDTO);
        ventaDTO.setProducto_fstock (productoFStockDTOS);

        return ventaDTO;
    }

    @Override
    @Transactional
    public void reembolsarVenta (VentaDTO ventaDTO){
        Venta venta = ventaRepository.findById (ventaDTO.getId ()).orElseThrow (() -> new RuntimeException ("Venta no encontrada"));
        venta.getVenta_productos ().forEach (vp -> {
            productoService.reembolsarProducto (vp.getProducto (),vp.getCantidad ());
            vp.setCantidad (0);
        });
        venta.getProductoFStocks ().forEach (pfs -> {
            pfs.setCantidad (0);
        });

        venta.setEstado (false);
        recalcularVenta (venta);
    }

    @Override
    @Transactional
    public void reembolsarProducto ( VentaDTO ventaSeleccionada, Venta_ProductoDTO vpd, int cantidadReembolsar ){
        Venta venta = ventaRepository.findById (ventaSeleccionada.getId ()).orElseThrow (() -> new RuntimeException ("Venta no encontrada"));

        Venta_Producto vp = venta.getVenta_productos ().stream()
                .filter (ventap -> { return ventap.getProducto ().getCodigobarras ().equals (vpd.getProducto ().getCodigobarras ());})
                .findFirst ().orElseThrow (() -> new RuntimeException ("Venta no encontrada"));

        if(cantidadReembolsar > vp.getCantidad ()) throw new IllegalArgumentException("Seleccione una cantidad menor o igual a la cantidad vendida");

        vp.setCantidad (vp.getCantidad () - cantidadReembolsar);

        productoService.reembolsarProducto (vp.getProducto (),cantidadReembolsar);

        recalcularVenta(venta);
     }

    @Override
    @Transactional
    public void reembolsarProducto ( VentaDTO ventaSeleccionada, ProductoFStockDTO pfsd, int cantidadReembolsar ){
        Venta venta = ventaRepository.findById (ventaSeleccionada.getId ()).orElseThrow (() -> new RuntimeException ("Venta no encontrada"));

        ProductoFStock productoFStock = venta.getProductoFStocks ().stream ()
                .filter (pfs -> { return pfs.getId () == pfsd.getId ();
                }).findFirst ().orElseThrow (() -> new RuntimeException ("Producto no encontrado"));

        if(cantidadReembolsar > productoFStock.getCantidad ())  throw new IllegalArgumentException("Seleccione una cantidad menor o igual a la cantidad vendida");

        productoFStock.setCantidad (pfsd.getCantidad () - cantidadReembolsar);

        recalcularVenta(venta);
    }

    private void recalcularVenta (Venta venta){
        double total= 0;
        System.out.println (venta.getVenta_productos ().size ());
        for(Venta_Producto vp : venta.getVenta_productos ()){
            double vpTotal= vp.getCantidad () * vp.getPrecio_venta ();
            double vpDescuento = 0;
            if(vp.getDescuento () > 0) vpDescuento = vpTotal * (1 -vp.getDescuento ());
            vp.setTotal(Math.round((vpTotal - vpDescuento)*100d) /100d);
            total += vp.getTotal ();
        }
        for(ProductoFStock pfs : venta.getProductoFStocks ()){
            double pfsTotal= pfs.getCantidad () * pfs.getPrecio_venta ();
            double pfsDescuento = 0;
            if(pfs.getDescuento () > 0) pfsDescuento = pfsTotal * (1 -pfs.getDescuento ());
            total+= pfs.getCantidad () *pfs.getPrecio_venta () * (1-pfs.getDescuento ()/100d);
        }
        double totalRedondeado = Math.round (total*100d)/100d;
        venta.setTotal (totalRedondeado);

        ventaRepository.save(venta);
    }

    private VentaDTO ventaToDto ( Venta v ){
        VentaDTO ventaDTO = new VentaDTO ();
        ventaDTO.setFecha(v.getFecha());
        ventaDTO.setEstado(v.isEstado());
        ventaDTO.setTotal(v.getTotal());
        ventaDTO.setId (v.getId());

        return ventaDTO;
    }

    private String generarVentaID (){
        String fechaActual = new SimpleDateFormat ("yyyyMMdd").format(new Date ());

        LocalDateTime inicioDelDia = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime finDelDia = LocalDateTime.now().with(LocalTime.MAX);

        String cuenta = ventaRepository.countByFecha (inicioDelDia,finDelDia) + "";

        return fechaActual + "-" + cuenta;
    }



}
