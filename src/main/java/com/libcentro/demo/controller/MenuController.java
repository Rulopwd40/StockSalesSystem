package com.libcentro.demo.controller;

import com.libcentro.demo.view.MenuFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MenuController {
    ViewController viewController;
    MenuFrame menuFrame;


    MenuController(ViewController viewController) {
        this.viewController = viewController;
    }


    //Abre menu restricciónes y listeners
    public void openMenuView(){
        menuFrame = new MenuFrame();
        menuFrame.setVisible(true);

        // Menu Listeners
        menuFrame.getProductosButton().addActionListener(e -> viewController.openProductosView());
        menuFrame.getVentaButton().addActionListener(e -> viewController.newVenta());
        menuFrame.getReportesButton().addActionListener(e -> viewController.openEstadisticasView());

        // KeyBindings for MenuFrame
        menuFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "openVenta");

        menuFrame.getRootPane().getActionMap().put("openVenta", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewController.newVenta();
            }
        });
    }
}