package com.libcentro.demo.view.venta;

import com.libcentro.demo.utils.filters.Filter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ApfsDialog extends JDialog {
    private JPanel apfsPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nombreField;
    private JTextField precioField;
    private JTextField cantField;
    private ArrayList<JTextField> textFields;

    public ApfsDialog() {

    }

    public void initialize(){
        setContentPane(apfsPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setSize(455,225);
        setLocationRelativeTo(null);

        setFilters();

        textFields = new ArrayList<>();
        textFields.add(nombreField);
        textFields.add(precioField);
        textFields.add(cantField);

    }

    private void setFilters() {
        Filter.setSymbolFilter(nombreField);
        Filter.setIntegerFilter(cantField);
        Filter.setPrecioFilter(precioField);
    }

    public ArrayList<JTextField> getTextFields() {
        return textFields;
    }

    public void onOK() {
        // add your code here
        dispose();
    }

    public void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public JPanel getApfsPane() {
        return apfsPane;
    }

    public JTextField getNombreField() {
        return nombreField;
    }

    public JTextField getPrecioField() {
        return precioField;
    }

    public JTextField getCantField() {
        return cantField;
    }

    public JButton getButtonOK() {
        return buttonOK;
    }

    public JButton getButtonCancel() {
        return buttonCancel;
    }
}
