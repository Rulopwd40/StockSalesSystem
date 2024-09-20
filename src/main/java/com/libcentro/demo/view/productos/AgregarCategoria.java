package com.libcentro.demo.view.productos;

import javax.swing.*;
import java.awt.event.*;

public class AgregarCategoria extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField categoriaField;
    private JTable tablaCategorias;
    private JButton agregarButton;

    public AgregarCategoria() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(600,400);
        createTable();

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
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

        setLocationRelativeTo(null);
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

    public JButton getButtonCancel() {
        return buttonCancel;
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
}
