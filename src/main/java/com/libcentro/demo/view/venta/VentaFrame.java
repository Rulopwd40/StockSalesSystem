package com.libcentro.demo.view.venta;

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
    private JPanel agregarProductoFieldPanel;

    public VentaFrame(){
        setContentPane(panelVenta);
        setSize(945,630);
        setLocationRelativeTo(null);
        createTable();
        setFocusable(true);

    }

    private void createTable(){
        table.setModel(new javax.swing.table.DefaultTableModel(
                null,
                new String[]{"Producto", "Cantidad", "Descuento(%)", "Precio"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Aquí defines qué columnas son editables
                switch (column) {
                    case 0:
                        return false;
                    case 1:
                        return true;// Cantidad
                    case 2:
                        return true;// Descuento(%)
                    case 3: // Precio
                        return true; // Estas columnas son editables
                    default:
                        return false; // Las demás columnas no son editables
                }
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0: // Producto
                        return String.class;
                    case 1: // Cantidad
                        return Integer.class; // o Double.class si manejas cantidades decimales
                    case 2: // Descuento(%)
                        return Float.class; // Descuento en porcentaje como decimal
                    case 3: // Precio
                        return Float.class; // Precio como decimal
                    default:
                        return Object.class;
                }
            }
        });
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

    public JLabel getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(JLabel totalPrice) {
        this.totalPrice = totalPrice;
    }

    public JPanel getAgregarProductoFieldPanel() {
        return agregarProductoFieldPanel;
    }

    public void setCantFocus() {
        cant.requestFocusInWindow();
    }
}
