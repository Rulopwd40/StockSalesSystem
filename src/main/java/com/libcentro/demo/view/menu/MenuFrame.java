package com.libcentro.demo.view.menu;

import javax.swing.*;

public class MenuFrame extends JFrame {
    private JButton productosButton;
    private JButton ventaButton;
    private JButton reportesButton;
    private JPanel panelMenu;
    private JPanel menuSub;

    private JPanel ButtonContainer0;
    private JPanel ButtonContainer1;
    private JPanel ButtonContainer2;
    private JLabel Menu;
    private JButton backUpButton;
    private JButton configuracionButton;

    public MenuFrame() {

    }

    public void initialize(){
        if (panelMenu == null) {
            throw new IllegalStateException("El panelMenu no está inicializado");
        }

        setContentPane(panelMenu);
        setFocusable(true);
        setTitle("Menu");
        setSize(600,400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JButton getProductosButton() {
        return productosButton;
    }

    public JButton getVentaButton() {
        return ventaButton;
    }

    public JButton getReportesButton() {
        return reportesButton;
    }

    public JButton getBackUpButton (){
        return backUpButton;
    }

    public JButton getConfiguracionButton (){
        return configuracionButton;
    }
}
