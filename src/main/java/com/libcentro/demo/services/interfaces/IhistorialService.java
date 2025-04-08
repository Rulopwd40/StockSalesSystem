package com.libcentro.demo.services.interfaces;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.model.dto.HistorialCostoDTO;
import com.libcentro.demo.model.dto.HistorialPrecioDTO;
import com.libcentro.demo.model.dto.PageDTO;

import java.util.List;

public interface IhistorialService {
    HistorialCosto crearHistorialCosto ( Producto nuevoProducto, double costoCompra, int cantidad, HistorialCosto.Estado estado );

    HistorialCosto findLastHistorialCosto ( Producto viejoProducto );

    HistorialCosto findNext ( Producto producto );

    void save ( HistorialCosto historialExistente );

    void save ( HistorialPrecio historialPrecio );

    void delete ( HistorialCosto historialCosto );

    void delete ( HistorialPrecio historialPrecio );

    HistorialPrecio findLastHistorialPrecio ( Producto producto );

    HistorialPrecio crearHistorialPrecio ( Producto producto, double precioVenta );

    HistorialCosto findHistorialInicial ( Producto producto );

    List<HistorialPrecioDTO> findAllPrecioByProducto ( String codbar );

    List<HistorialCostoDTO> findAllCostoByProducto ( String codbar );

    HistorialCosto findAnterior(HistorialCosto historialExistente);

    HistorialCosto findSiguiente ( HistorialCosto historialCosto );

    void deleteAllByCodigo ( String codigobarras );

    PageDTO<HistorialPrecioDTO> findPagePrecioByProducto ( String codbar, int historialPrecioPage );

    PageDTO<HistorialCostoDTO> findPageCostoByProducto ( String codbar, int historialCostoPage );
}
