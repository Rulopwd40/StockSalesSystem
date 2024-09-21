package com.libcentro.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.libcentro.demo.model.Producto;
import com.libcentro.demo.repository.IproductoRepository;
import com.libcentro.demo.services.interfaces.IproductoService;

@Service
public class ProductoService implements IproductoService {
    @Autowired
    private IproductoRepository productoRepo;

    @Override
    public List<Producto> getAll() {
        return productoRepo.findAll();
    }

    @Override
    public void saveProducto(Producto x) {
        // Verificar si el producto ya existe en la base de datos
        if (x.getCodigo_barras() != null) {
            Producto existingProducto = productoRepo.findById(x.getCodigo_barras()).orElse(null);
            if (existingProducto != null) {
                throw new ObjectOptimisticLockingFailureException(Producto.class, x.getCodigo_barras());
            }
        }
        productoRepo.save(x);
    }

    @Override
    public void deleteProducto(Producto x) {
        productoRepo.delete(x);
    }

    @Override
    public void updateProducto(Producto x) {
        productoRepo.save(x);
    }

}
