package com.libcentro.demo.view.productos;

import javax.swing.*;
import java.awt.event.*;

public class AgregarCategoria extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField categoriaField;
    private JTable tablaCategorias;
    private JButton agregarButton;
    private JButton eliminarButton;

    public AgregarCategoria() {

    }

    public void initialize(){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(600,400);
        createTable();
        setLocationRelativeTo(null);
        addListeners();
    }

    private void addListeners(){
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    private void createTable() {
        tablaCategorias.setModel(new javax.swing.table.DefaultTableModel(
                null,
                new String[]{"Cantidad"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Aquí defines qué columnas son editables
                switch (column) {
                    case 0:
                        return true;
                    default:
                        return false; // Las demás columnas no son editables
                }
            }
        });
        tablaCategorias.getTableHeader().setReorderingAllowed(false);
        tablaCategorias.setAutoCreateRowSorter(true);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public JButton getButtonOK() {
        return buttonOK;
    }


    public JTextField getCategoriaField() {
        return categoriaField;
    }

    public JTable getTablaCategorias() {
        return tablaCategorias;
    }

    public JButton getAgregarButton() {
        return agregarButton;
    }

    public JButton getEliminarButton() {
        return eliminarButton;
    }
}
