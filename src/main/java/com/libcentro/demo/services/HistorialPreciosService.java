package com.libcentro.demo.services;

import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.repository.IhistorialpreciosRepository;
import com.libcentro.demo.services.interfaces.IhistorialPreciosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class HistorialPreciosService implements IhistorialPreciosService {

        @Autowired
        IhistorialpreciosRepository repository;

        @Override
        public void saveAll(Set<HistorialPrecio> historialPrecios){
            repository.saveAll(historialPrecios);
        }
        @Override
        public void deleteAll(Set<HistorialPrecio> historialPrecios){
            repository.deleteAll(historialPrecios);
        }

    @Override
    public HistorialPrecio findFirstByProductoOrderByIdDesc(Producto nuevoProducto) {
        return repository.findFirstByProductoOrderByIdDesc(nuevoProducto);
    }

    @Override
    public void delete(HistorialPrecio historialReciente) {
        repository.delete(historialReciente);
    }

    @Override
    public void save(HistorialPrecio historialPrecio) {
        repository.save(historialPrecio);
    }

    @Override
    public Set<HistorialPrecio> findAllByProducto(Producto producto) {
        return repository.findByProductoOrderByIdDesc(producto);
    }


}
