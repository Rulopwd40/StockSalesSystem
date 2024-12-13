package com.libcentro.demo.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.libcentro.demo.model.Producto;
import com.libcentro.demo.model.dto.CategoriaDTO;
import com.libcentro.demo.model.dto.ProductoDTO;
import com.libcentro.demo.model.dto.VentaDTO;
import com.libcentro.demo.model.dto.Venta_ProductoDTO;
import com.libcentro.demo.utils.GeneradorTicket;
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

    @Override
    public List<Venta> getAll() {
            return ventaRepo.findAll();
    }

    @Override
    public Venta saveVenta(Venta x) {
        return ventaRepo.save(x);
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
    public void vender( VentaDTO venta) throws RuntimeException {
        GeneradorTicket generadorTicket=new GeneradorTicket();


        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = dateTime.format(dateTimeFormatter);

        venta.setFecha(formattedDateTime);

        Venta ventaID = saveVenta(venta);

        for(Venta_ProductoDTO ventaProducto : venta.getVenta_producto()){
            Producto productoVenta= ventaProducto.getProducto();
            Producto productoO = productoService.getProducto(productoVenta.getCodigobarras ());

            CategoriaDTO categoriaDTO = new CategoriaDTO(productoO.getCategoria ());

            ProductoDTO productoDTO = new ProductoDTO (productoO.getCodigobarras (),
                    productoO.getNombre (),
                    categoriaDTO,
                    productoO.getCosto_compra (),
                    productoO.getPrecio_venta (),
                    productoO.getStock ());

            productoDTO.setStock(productoO.getStock()-ventaProducto.getCantidad());
            productoService.updateProducto(productoDTO);
        }


        String ticket = generadorTicket.generarTicket(ventaID);
        generadorTicket.imprimirTicket();
    }

}
