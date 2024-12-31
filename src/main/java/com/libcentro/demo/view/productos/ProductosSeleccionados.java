package com.libcentro.demo.view.productos;

import javax.swing.*;

public class ProductosSeleccionados extends JDialog {
    private JTable table;
    private JTextField precioField;
    private JComboBox categoriaBox;
    private JButton okButton;
    private JPanel panelAct;
    private JButton actualizarPrecio;
    private JButton actualizarCategoria;


    public ProductosSeleccionados(){
        setContentPane(panelAct);
        setSize(600,400);
        setLocationRelativeTo(null);
        setFocusable(true);
        setModal(true);
        setResizable (false);
    }

    public JTextField getPrecioField (){
        return precioField;
    }

    public JComboBox getCategoriaBox (){
        return categoriaBox;
    }

    public JButton getOkButton (){
        return okButton;
    }


    public JTable getTable (){
        return table;
    }

    public JButton getActualizarPrecio (){
        return actualizarPrecio;
    }

    public JButton getActualizarCategoria (){
        return actualizarCategoria;
    }
}
