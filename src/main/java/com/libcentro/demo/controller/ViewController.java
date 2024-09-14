package com.libcentro.demo.controller;

import com.libcentro.demo.view.ApfsDialog;
import com.libcentro.demo.view.MenuFrame;
import com.libcentro.demo.view.VentaFrame;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class ViewController {

    MenuFrame menuFrame;
    VentaFrame ventaFrame;
    ApfsDialog apfsDialog;

    MenuController menuController;
    VentaController ventaController;
    ProductosController productosController;

    public ViewController(MenuController menuController, VentaController ventaController, ProductosController productosController) {
        this.menuController = menuController;
        this.ventaController = ventaController;
        this.productosController = productosController;
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