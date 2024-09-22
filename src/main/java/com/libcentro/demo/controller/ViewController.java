package com.libcentro.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class ViewController {

    MenuController menuController;
    VentaController ventaController;
    ProductosController productosController;

    @Autowired
    public ViewController(MenuController menuController, VentaController ventaController, ProductosController productosController) {
        this.menuController = menuController;
        this.ventaController = ventaController;
        this.productosController = productosController;


    }

    void openMenuView() {
        menuController.openMenuView();
    }


    void openProductosView() {
        productosController.openProductosFrame();
    }


    //Crear Venta
    void newVenta(){
        ventaController.openVentaFrame();
    }

    void openEstadisticasView() {
        System.out.println("open estadisticas view");
    }
}