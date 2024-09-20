package com.libcentro.demo.view.productos;

import com.libcentro.demo.utils.filters.Filter;

import javax.swing.*;
import java.awt.event.*;

public class AgregarProducto extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField codigoField;
    private JTextField nombreField;
    private JTextField precioField;
    private JComboBox<String>categoriaBox;
    private JTextField costoField;
    private JTextField cantidadField;
    private JPanel errorPanel;

    public AgregarProducto() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(600,325);
        setLocationRelativeTo(null);

        setFilters();




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
    }

    private void setFilters() {
        Filter.setIntegerFilter(codigoField);
        Filter.setSymbolFilter(nombreField);
        Filter.setPrecioFilter(precioField);
        Filter.setPrecioFilter(costoField);
        Filter.setIntegerFilter(cantidadField);
    }

    public void onOK() {
        // add your code here
        dispose();
    }

    public void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public JTextField getCantidadField() {
        return cantidadField;
    }

    public JTextField getCostoField() {
        return costoField;
    }

    public JComboBox<String> getCategoriaBox() {
        return categoriaBox;
    }

    public JTextField getPrecioField() {
        return precioField;
    }

    public JTextField getNombreField() {
        return nombreField;
    }

    public JTextField getCodigoField() {
        return codigoField;
    }

    public JButton getButtonOK() {
        return buttonOK;
    }

    public JPanel getErrorPanel() {
        return errorPanel;
    }
}
