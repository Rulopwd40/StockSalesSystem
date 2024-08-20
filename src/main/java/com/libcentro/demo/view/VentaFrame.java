package com.libcentro.demo.view;

import javax.swing.*;

public class VentaFrame extends JFrame {
    private JPanel panelVenta;
    private JTable table;
    private JTextField textField1;
    private JTextField textField2;
    private JButton agregarEnterButton;
    private JButton agregarProductoFueraDeButton;
    private JButton generarTicketF3Button;
    private JButton cancelarEscButton;

    public VentaFrame(){
        setContentPane(panelVenta);
        setSize(945,630);
        createTable();

    }

    private void createTable(){
        table.setModel(new javax.swing.table.DefaultTableModel(
                null, new String[]{"Producto","Cantidad","Descuento","Precio"}
                ));

    }
}
