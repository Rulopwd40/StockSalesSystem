package com.libcentro.demo.view.productos;

import javax.swing.*;

public class ProductosFrame extends JFrame {
    private JTable table;
    private JTextField buscarField;
    private JCheckBox sinStockCheckBox;
    private JButton agregarCategoriaButton;
    private JButton unProductoButton;
    private JButton importarCsvButton;
    private JButton actualizarUnProductoButton;
    private JButton porCategoriaButton;
    private JButton generalButton;
    private JButton guardarButton;
    private JButton deshacerTodoButton;
    private JButton deshacerCambiosButton;
    private JPanel panelProducto;
    private JButton eliminarProductoButton;
    private JPanel panelBusqueda;
    private JPanel panelAdd;
    private JPanel panelUpd;
    private JPanel panelClose;
    private JButton actualizarTablaButton;
    private JButton siguienteButton;
    private JButton anteriorButton;
    private JLabel pageCount;


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
                new String[]{"Cod.", "Nombre", "Categoria", "Cantidad","Costo Compra", "Precio U."}
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
                    case 4:
                        return true;
                    default:
                        return false; // Las demás columnas no son editables
                }
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0: // Cod
                        return String.class;
                    case 1:
                        return String.class; // o Double.class si manejas cantidades decimales
                    case 2:
                        return String.class; // Descuento en porcentaje como decimal
                    case 3:
                        return Integer.class; // Precio como decimal
                    case 4:
                        return Float.class;
                    default:
                        return Object.class;
                }
            }
        });
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoCreateRowSorter(true);


    }

    public JButton getAgregarCategoriaButton() {
        return agregarCategoriaButton;
    }

    public JButton getUnProductoButton() {
        return unProductoButton;
    }

    public JButton getImportarCsvButton() {
        return importarCsvButton;
    }

    public JButton getActualizarUnProductoButton() {
        return actualizarUnProductoButton;
    }

    public JButton getPorCategoriaButton() {
        return porCategoriaButton;
    }

    public JButton getGeneralButton() {
        return generalButton;
    }

    public JButton getGuardarButton() {
        return guardarButton;
    }

    public JButton getDeshacerTodoButton() {
        return deshacerTodoButton;
    }

    public JButton getDeshacerCambiosButton() {
        return deshacerCambiosButton;
    }

    public JButton getEliminarProductoButton() {
        return eliminarProductoButton;
    }

    public JTextField getBuscarField() {
        return buscarField;
    }

    public JCheckBox getSinStockCheckBox() {
        return sinStockCheckBox;
    }

    public JTable getTable() {
        return table;
    }

    public JButton getActualizarTablaButton() {
        return actualizarTablaButton;
    }

    public JButton getAnteriorButton (){
        return anteriorButton;
    }

    public JButton getSiguienteButton (){
        return siguienteButton;
    }

    public JLabel getPageCount (){
        return pageCount;
    }
}
