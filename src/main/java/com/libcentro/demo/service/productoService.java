package com.libcentro.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libcentro.demo.model.producto;
import com.libcentro.demo.repository.IproductoRepository;
import com.libcentro.demo.service.interfaces.IproductoService;

@Service
public class productoService implements IproductoService {
    @Autowired
    private IproductoRepository productoRepo;

    @Override
    public List<producto> getAll() {
        return productoRepo.findAll();
    }

    @Override
    public void saveProducto(producto x) {
        productoRepo.save(x);
    }

    @Override
    public void deleteProducto(producto x) {
        productoRepo.delete(x);
    }

    @Override
    public void updateProducto(producto x) {
        productoRepo.save(x);
    }

}
