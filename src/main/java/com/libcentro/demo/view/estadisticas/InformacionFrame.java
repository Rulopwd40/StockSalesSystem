package com.libcentro.demo.view.estadisticas;

import javax.swing.*;

public class InformacionFrame extends JFrame {
    private JButton buscarButton;
    private JTextField buscarField;
    private JButton mostrarButton;
    private JButton eliminarButton;
    private JButton reembolsarButton;
    private JTable productosTable;
    private JTable ventaTable;
    private JScrollPane JScrollPane;
    private JPanel panel;
    private JButton anteriorButton;
    private JButton siguienteButton;
    private JLabel pageCount;
    private JTable pfsTable;
    private JLabel hora;
    private JButton reembolsarTodoButton;

    public InformacionFrame() {
        super("Informacion");

        setContentPane (panel);
        setSize(1056,524);
        setLocationRelativeTo(null);
        setResizable (false);
    }

    public JButton getBuscarButton (){
        return buscarButton;
    }

    public JTextField getBuscarField (){
        return buscarField;
    }

    public JButton getMostrarButton (){
        return mostrarButton;
    }

    public JButton getEliminarButton (){
        return eliminarButton;
    }

    public JButton getReembolsarButton (){
        return reembolsarButton;
    }

    public JTable getProductosTable (){
        return productosTable;
    }

    public JTable getVentaTable (){
        return ventaTable;
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

    public JTable getPfsTable (){
        return pfsTable;
    }

    public JLabel getHora (){
        return hora;
    }

    public JButton getReembolsarTodoButton (){
        return reembolsarTodoButton;
    }
}
