package com.libcentro.demo.services.interfaces;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import org.springframework.stereotype.Service;

@Service
public interface IhistorialService {
    void crearHistorialCosto ( Producto nuevoProducto, double costoCompra, int cantidad, HistorialCosto.Estado estado );

    HistorialCosto findLastHistorialCosto ( Producto viejoProducto );

    void save ( HistorialCosto historialExistente );

    void save ( HistorialPrecio historialPrecio );
}
