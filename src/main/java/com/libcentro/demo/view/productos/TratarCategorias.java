package com.libcentro.demo.view.productos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class TratarCategorias extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable tablaCategorias;
    private JButton crearButton;
    private JButton elegirOtraCategoriaButton;
    private JButton anularButton;
    private JPanel optionalPane;
    private JButton elegirButton;
    private JTable tablaCategoriasExistentes;

    public TratarCategorias() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(new Dimension(450,300));
        setLocationRelativeTo(null);
        createTable();
        setUndecorated(true);
    }

    private void createTable(){
        tablaCategorias.setModel(new DefaultTableModel(null,new String[]{"Categoria"}){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        });
        tablaCategoriasExistentes.setModel(new DefaultTableModel(null,new String[]{"Categoria"}){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        });
    }

    public void onOK() {
        // add your code here
        dispose();
    }


    public JButton getAnularButton() {
        return anularButton;
    }

    public JButton getElegirOtraCategoriaButton() {
        return elegirOtraCategoriaButton;
    }

    public JButton getCrearButton() {
        return crearButton;
    }

    public JTable getTablaCategorias() {
        return tablaCategorias;
    }

    public JTable getTablaCategoriasExistentes() {
        return tablaCategoriasExistentes;
    }

    public JButton getElegirButton() {
        return elegirButton;
    }

    public JPanel getOptionalPane() {
        return optionalPane;
    }

    public JButton getButtonOK() {
        return buttonOK;
    }
}
