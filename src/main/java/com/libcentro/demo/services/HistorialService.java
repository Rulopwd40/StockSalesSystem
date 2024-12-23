package com.libcentro.demo.services;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.repository.IhistorialCostoRepository;
import com.libcentro.demo.repository.IhistorialPrecioRepository;
import com.libcentro.demo.services.interfaces.IhistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class HistorialService implements IhistorialService {

    @Autowired
    IhistorialCostoRepository historialCostoRepository;
    @Autowired
    IhistorialPrecioRepository historialPrecioRepository;


    @Override
    public HistorialCosto crearHistorialCosto ( Producto nuevoProducto, double costoCompra, int cantidad, HistorialCosto.Estado estado ){
            HistorialCosto historialCosto = new HistorialCosto();
            historialCosto.setCantidad(cantidad);
            historialCosto.setEstado(estado);
            historialCosto.setProducto(nuevoProducto);
            historialCosto.setCosto_compra (costoCompra);

            Date fecha = new Date ();
            SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy");
            String fechaFormateada = sdf.format(fecha);
            historialCosto.setFecha(fechaFormateada);

            return historialCosto;
    }
    @Override
    public HistorialPrecio crearHistorialPrecio ( Producto producto, double precioVenta ){
        HistorialPrecio historialPrecio = new HistorialPrecio();
        historialPrecio.setPrecio_venta (precioVenta);
        historialPrecio.setProducto(producto);
        Date fecha = new Date ();
        SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy");
        String fechaFormateada = sdf.format(fecha);
        historialPrecio.setFecha(fechaFormateada);

        return historialPrecio;
    }



    @Override
    public void save ( HistorialCosto historialExistente ){
        historialCostoRepository.save(historialExistente);
    }

    @Override
    public void save ( HistorialPrecio historialPrecio ){
        historialPrecioRepository.save(historialPrecio);
    }

    @Override
    public void delete ( HistorialCosto historialCosto ){
        historialCostoRepository.delete(historialCosto);
    }

    @Override
    public void delete ( HistorialPrecio historialPrecio ){
        historialPrecioRepository.delete(historialPrecio);
    }

    @Override
    public HistorialPrecio findLastHistorialPrecio ( Producto producto ){
        return historialPrecioRepository.findFirstByProductoCodigobarrasOrderByIdDesc (producto.getCodigobarras ());
    }
    @Override
    public HistorialCosto findLastHistorialCosto ( Producto producto ){
        return historialCostoRepository.findFirstByProductoCodigobarrasOrderByIdDesc (producto.getCodigobarras ());
    }


}
