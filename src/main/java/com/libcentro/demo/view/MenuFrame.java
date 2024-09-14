package com.libcentro.demo.view;

import jakarta.annotation.PostConstruct;

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

    public MenuFrame() {
        setContentPane(panelMenu);
        setFocusable(true);
        setTitle("Menu");
        setSize(600,400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        createUIComponents();
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

    private void createUIComponents() {
    }
}
