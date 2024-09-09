package com.libcentro.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.libcentro.demo.model.Producto;

import com.libcentro.demo.services.productoService;

@Controller
public class ProductoController {
    @Autowired
    private productoService productoService;

    public List<Producto> getAll() {
        return productoService.getAll();
    }

    public void cargarMasiva(List<Producto> testInventario) {
        for (Producto d : testInventario) {
            productoService.saveProducto(d);
        }
    }

    public void listarProductos() {
        for (Producto d : productoService.getAll()) {
            System.out.println(d.toString());
        }
    }
    public void saveProducto(Producto x) {
        productoService.saveProducto(x);
    }
}
