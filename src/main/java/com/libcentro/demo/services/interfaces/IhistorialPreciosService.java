package com.libcentro.demo.services.interfaces;

import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;

import java.util.Set;

public interface IhistorialPreciosService {

    void saveAll(Set<HistorialPrecio> historialPrecios);

    void deleteAll(Set<HistorialPrecio> historialPrecios);

    HistorialPrecio findFirstByProductoOrderByIdDesc(Producto nuevoProducto);

    void delete(HistorialPrecio historialReciente);

    void save(HistorialPrecio historialPrecio);

    Set<HistorialPrecio> findAllByProducto(Producto producto);
}
