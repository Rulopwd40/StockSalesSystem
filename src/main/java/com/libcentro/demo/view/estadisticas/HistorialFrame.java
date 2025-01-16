package com.libcentro.demo.view.estadisticas;

import javax.swing.*;

public class HistorialFrame extends JFrame {
    private JPanel panel;
    private JButton okButton;
    private JTextField buscarField;
    private JButton buscarButton;
    private JTable costosTable;
    private JTable preciosTable;

    public HistorialFrame () {
        this.setTitle("Historial");

        setContentPane(panel);
        pack ();
        setSize (1360,600);
        setLocationRelativeTo(null);
        setResizable (false);
    }

    public JButton getOkButton (){
        return okButton;
    }

    public JTextField getBuscarField (){
        return buscarField;
    }

    public JButton getBuscarButton (){
        return buscarButton;
    }

    public JTable getCostosTable (){
        return costosTable;
    }

    public JTable getPreciosTable (){
        return preciosTable;
    }
}
