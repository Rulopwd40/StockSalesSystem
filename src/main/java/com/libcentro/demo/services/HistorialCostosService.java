package com.libcentro.demo.services;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.repository.IhistorialcostosRepository;
import com.libcentro.demo.services.interfaces.IhistorialCostosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class HistorialCostosService implements IhistorialCostosService {

            @Autowired
            IhistorialcostosRepository repository;

            @Override
            public void saveAll(Set<HistorialCosto> historialCostos) {
                repository.saveAll(historialCostos);
            }
            @Override
            public void deleteAll(Set<HistorialCosto> historialCostos){
                repository.deleteAll(historialCostos);
            }

    @Override
    public void save(HistorialCosto nuevoHistorial) {
        repository.save(nuevoHistorial);
    }

    @Override
    public HistorialCosto findFirstByProductoOrderByIdDesc(Producto nuevoProducto) {
        return repository.findFirstByProductoOrderByIdDesc(nuevoProducto);
    }

    @Override
    public void delete(HistorialCosto ultimoHistorial) {
        repository.delete(ultimoHistorial);
    }

    @Override
    public Set<HistorialCosto> findAllByProducto(Producto producto) {
        return repository.findAllByProductoOrderByIdDesc(producto);
    }

}
