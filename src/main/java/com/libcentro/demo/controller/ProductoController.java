package com.libcentro.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.libcentro.demo.model.producto;

import com.libcentro.demo.service.productoService;

@Controller
public class ProductoController {
    @Autowired
    private productoService productoService;

    public List<producto> getAll() {
        return productoService.getAll();
    }

    public void cargarMasiva(List<producto> testInventario) {
        for (producto d : testInventario) {
            productoService.saveProducto(d);
        }
    }

    public void listarProductos() {
        for (producto d : productoService.getAll()) {
            System.out.println(d.toString());
        }
    }
    public void saveProducto(producto x) {
        productoService.saveProducto(x);
    }
}
