package com.libcentro.demo.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.libcentro.demo.model.Producto;
import com.libcentro.demo.model.ProductoFStock;
import com.libcentro.demo.model.Venta_Producto;
import com.libcentro.demo.model.dto.CategoriaDTO;
import com.libcentro.demo.model.dto.ProductoDTO;
import com.libcentro.demo.model.dto.VentaDTO;
import com.libcentro.demo.model.dto.Venta_ProductoDTO;
import com.libcentro.demo.repository.IproductoRepository;
import com.libcentro.demo.utils.GeneradorTicket;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libcentro.demo.model.Venta;
import com.libcentro.demo.repository.IventaRepository;
import com.libcentro.demo.services.interfaces.IventaService;
@Service
public class VentaService implements IventaService {
    @Autowired
    private IventaRepository ventaRepo;
    @Autowired
    private ProductoService productoService;
    @Autowired
    private IproductoRepository productoRepository;

    @Override
    public List<Venta> getAll() {
            return ventaRepo.findAll();
    }

    @Override
    @Transactional
    public Venta saveVenta(VentaDTO ventaDTO) {
        Venta venta = new Venta();
        venta.setFecha(ventaDTO.getFecha());
        venta.setEstado(ventaDTO.isEstado());
        venta.setTotal(ventaDTO.getTotal());


        Venta savedVenta = ventaRepo.save(venta);


        savedVenta.setProductoFStocks(ventaDTO.getProducto_fstock().stream()
                .map(ProductoFStock::new)
                .collect(Collectors.toSet()));

        ventaDTO.getVenta_producto().forEach(vpd -> {
            Venta_Producto ventaproducto = new Venta_Producto(vpd);
            ventaproducto.setVenta(savedVenta);
            Optional<Producto> producto = productoRepository.findById(vpd.getProducto().getCodigobarras());
            producto.ifPresent(p -> {
                ventaproducto.setProducto(p, vpd.getCantidad());
            });
            savedVenta.getVenta_productos().add(ventaproducto);
        });

        return ventaRepo.save(savedVenta);
    }

    @Override
    public void deleteVenta(Venta x) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteVenta'");
    }

    @Override
    public void updateVenta(Venta x) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateVenta'");
    }

    @Override
    public void vender( VentaDTO ventaDTO) throws RuntimeException {
        GeneradorTicket generadorTicket=new GeneradorTicket();


        LocalDateTime dateTime = LocalDateTime.now();
        ventaDTO.setFecha(dateTime);


        Venta venta = saveVenta(ventaDTO);

        for(Venta_Producto ventaProducto : venta.getVenta_productos()){
            Producto productoVenta= ventaProducto.getProducto();
            ProductoDTO productoO = productoService.getProducto(productoVenta.getCodigobarras ());

            CategoriaDTO categoriaDTO =productoO.getCategoria ();

            ProductoDTO productoDTO = new ProductoDTO (productoO.getCodigobarras (),
                    productoO.getNombre (),
                    categoriaDTO,
                    productoO.getCosto_compra (),
                    productoO.getPrecio_venta (),
                    productoO.getStock ());

            productoDTO.setStock(productoO.getStock()-ventaProducto.getCantidad());
            productoService.venderProducto (productoDTO);
        }


        String ticket = generadorTicket.generarTicket(venta);
        generadorTicket.imprimirTicket();
    }

}
