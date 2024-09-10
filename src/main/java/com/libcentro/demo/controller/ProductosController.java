package com.libcentro.demo.controller;

import java.util.List;

import com.libcentro.demo.view.ProductosFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.libcentro.demo.model.Producto;

import com.libcentro.demo.services.productoService;

@Controller
public class ProductosController {
    @Autowired
    private productoService productoService;
    ViewController viewController;
    ProductosFrame productosFrame;

    public ProductosController(ViewController viewController) {
        this.viewController = viewController;
    }

    public void openProductosFrame(){
        if(productosFrame == null){
            productosFrame = new ProductosFrame();
            productosFrame.setVisible(true);
        }

    }


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
