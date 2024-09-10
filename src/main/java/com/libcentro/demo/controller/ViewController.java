package com.libcentro.demo.controller;

import com.libcentro.demo.view.ApfsDialog;
import com.libcentro.demo.view.MenuFrame;
import com.libcentro.demo.view.VentaFrame;

public class ViewController {

    MenuFrame menuFrame;
    VentaFrame ventaFrame;
    ApfsDialog apfsDialog;

    MenuController menuController;
    VentaController ventaController;
    ProductosController productosController;

    public ViewController() {
        menuController = new MenuController(this);
        ventaController = new VentaController(this);
        productosController = new ProductosController(this);
        openMenuView();

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