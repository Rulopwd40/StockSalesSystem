package com.libcentro.demo.services.interfaces;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.Producto;

import java.util.Set;

public interface IhistorialCostosService {
    void saveAll(Set<HistorialCosto> historialCostos);

    void deleteAll(Set<HistorialCosto> historialCostos);

    void save(HistorialCosto nuevoHistorial);

    HistorialCosto findFirstByProductoOrderByIdDesc(Producto nuevoProducto);

    void delete(HistorialCosto ultimoHistorial);

    Set<HistorialCosto> findAllByProducto(Producto producto);
}
