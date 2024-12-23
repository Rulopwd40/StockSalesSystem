package com.libcentro.demo.services.interfaces;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;

public interface IhistorialService {
    HistorialCosto crearHistorialCosto ( Producto nuevoProducto, double costoCompra, int cantidad, HistorialCosto.Estado estado );

    HistorialCosto findLastHistorialCosto ( Producto viejoProducto );

    void save ( HistorialCosto historialExistente );

    void save ( HistorialPrecio historialPrecio );

    void delete ( HistorialCosto historialCosto );

    void delete ( HistorialPrecio historialPrecio );

    HistorialPrecio findLastHistorialPrecio ( Producto producto );

    HistorialPrecio crearHistorialPrecio ( Producto producto, double precioVenta );
}
