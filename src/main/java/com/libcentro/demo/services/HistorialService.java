package com.libcentro.demo.services;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.services.interfaces.IhistorialService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class HistorialService implements IhistorialService {
    @Override
    public HistorialCosto crearHistorialCosto ( Producto nuevoProducto, double costoCompra, int cantidad, HistorialCosto.Estado estado ){
            HistorialCosto historialCosto = new HistorialCosto();
            historialCosto.setCantidad(cantidad);
            historialCosto.setEstado(estado);
            historialCosto.setProducto(nuevoProducto);

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

    }

    @Override
    public void save ( HistorialPrecio historialPrecio ){

    }

    @Override
    public void delete ( HistorialCosto historialCosto ){

    }

    @Override
    public void delete ( HistorialPrecio historialPrecio ){

    }

    @Override
    public HistorialPrecio findLastHistorialPrecio ( Producto producto ){
        return null;
    }
    @Override
    public HistorialCosto findLastHistorialCosto ( Producto viejoProducto ){
        return null;
    }


}
