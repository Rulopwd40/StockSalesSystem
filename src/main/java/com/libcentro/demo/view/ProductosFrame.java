package com.libcentro.demo.view;

import javax.swing.JFrame;

public class ProductosFrame {
    private JFrame frame;

    public ProductosFrame(){
        initialize();
    }

    private void initialize(){
        frame = new JFrame();
        frame.setTitle("Menú");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 525);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}
