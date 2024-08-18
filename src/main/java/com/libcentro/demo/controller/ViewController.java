package com.libcentro.demo.controller;

import com.libcentro.demo.view.MenuFrame;

import javax.swing.*;

public class ViewController {

    MenuFrame menuFrame;
    public ViewController(MenuFrame menuFrame) {
        this.menuFrame = menuFrame;


        // Añadir ActionListeners a los botones desde el controlador
        this.menuFrame.getProductosButton().addActionListener(e -> openProductosView());
        this.menuFrame.getVentaButton().addActionListener(e -> openVentaView());
        this.menuFrame.getEstadisticasButton().addActionListener(e -> openEstadisticasView());



    }
    private void openProductosView() {
        System.out.println("open productos view");
    }

    private void openVentaView() {
        System.out.println("open venta view");
    }

    private void openEstadisticasView() {
        System.out.println("open estadisticas view");
    }

}
