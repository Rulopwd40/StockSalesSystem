package com.libcentro.demo.view;

import javax.swing.*;

public class VentaFrame extends JFrame {
    private JPanel panelVenta;
    private JTable table;
    private JTextField codBar;
    private JTextField cant;
    private JButton agregarEnterButton;
    private JButton agregarProductoFueraDeButton;
    private JButton generarTicketF3Button;
    private JButton cancelarEscButton;
    private JLabel totalPrice;

    public VentaFrame(){
        setContentPane(panelVenta);
        setSize(945,630);
        setLocationRelativeTo(null);
        createTable();
        setFocusable(true);

    }

    private void createTable(){
        table.setModel(new javax.swing.table.DefaultTableModel(
                null, new String[]{"Producto","Cantidad","Descuento","Precio"}
                ));
        table.getTableHeader().setReorderingAllowed(false);

    }

    public void setCodFocus(){
        codBar.requestFocusInWindow();
    }
    public JButton getAgregarEnterButton() {
        return agregarEnterButton;
    }

    public JButton getAgregarProductoFueraDeButton() {
        return agregarProductoFueraDeButton;
    }

    public JButton getGenerarTicketF3Button() {
        return generarTicketF3Button;
    }

    public JButton getCancelarEscButton() {
        return cancelarEscButton;
    }

    public JTextField getCodBar() {
        return codBar;
    }

    public JTextField getCant() {
        return cant;
    }

    public JTable getTable() {
        return table;
    }
}
