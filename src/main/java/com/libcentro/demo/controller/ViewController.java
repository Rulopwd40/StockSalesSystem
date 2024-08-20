package com.libcentro.demo.controller;

import com.libcentro.demo.view.MenuFrame;
import com.libcentro.demo.view.VentaFrame;


public class ViewController {

    MenuFrame menuFrame;
    VentaFrame ventaFrame;

    public ViewController() {
        menuFrame = new MenuFrame();
        ventaFrame = new VentaFrame();
        menuFrame.setVisible(true);

        // Añadir ActionListeners a los botones desde el controlador
        menuFrame.getProductosButton().addActionListener(e -> openProductosView());
        menuFrame.getVentaButton().addActionListener(e -> openVentaView());
        menuFrame.getReportesButton().addActionListener(e -> openEstadisticasView());



    }
    private void openProductosView() {
        System.out.println("open productos view");
    }

    private void openVentaView() {
        ventaFrame.setVisible(true);
    }

    private void openEstadisticasView() {
        System.out.println("open estadisticas view");
    }

}
