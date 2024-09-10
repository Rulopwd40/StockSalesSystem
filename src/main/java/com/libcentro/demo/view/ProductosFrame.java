package com.libcentro.demo.view;

import javax.swing.*;

public class ProductosFrame extends JFrame {
    private JTable table;
    private JTextField textField1;
    private JCheckBox sinStockCheckBox;
    private JButton agregarCategoriaButton;
    private JButton unProductoButton;
    private JButton importarCsvButton;
    private JButton unProductoButton1;
    private JButton porCategoriaButton;
    private JButton generalButton;
    private JButton volverButton;
    private JButton guardarYVolverButton;
    private JButton deshacerCambiosButton;
    private JPanel panelProducto;
    private JButton eliminarProductoButton;

    public ProductosFrame(){
        setContentPane(panelProducto);
        setSize(945,630);
        setLocationRelativeTo(null);
        createTable();
        setFocusable(true);
    }

    private void createTable(){
        table.setModel(new javax.swing.table.DefaultTableModel(
                null,
                new String[]{"Cod.", "Nombre", "Categoria", "Cantidad", "Precio U."}
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

}
