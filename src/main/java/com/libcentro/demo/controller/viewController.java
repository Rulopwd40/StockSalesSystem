package com.libcentro.demo.controller;

import com.libcentro.demo.view.MenuFrame;
import com.libcentro.demo.view.ProductosFrame;
import com.libcentro.demo.view.Viewgen;

public class viewController {

    MenuFrame menu;
    ProductosFrame pview;
    public viewController(MenuFrame menu,ProductosFrame pview){
        this.menu= menu;
        this.pview= pview;
        //Menu
        this.menu.getEstadisticasButton().addActionListener(e-> handleEstadisticasButton());;
        this.menu.getPreciosButton().addActionListener(e-> handlePreciosButton());
        this.menu.getProductosButton().addActionListener(e-> handleProductosButton());

    }

    //Productos
    private void handleProductosButton() {
        navigateToProductosFrame();
    }

    //Precios
    private void handlePreciosButton() {
    
        System.out.println("Prices");
    }

    //Stats
    private void handleEstadisticasButton() {
    
        System.out.println("Stats");
    }

    // Método para navegar a la vista de productos
    private void navigateToProductosFrame() {
        menu.setVisible(false);  // Oculta la ventana del menú
        pview.setVisible(true);   // Muestra la ventana de productos
    }
}
