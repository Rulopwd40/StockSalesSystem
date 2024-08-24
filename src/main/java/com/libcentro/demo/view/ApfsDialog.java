package com.libcentro.demo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
        setContentPane(apfsPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setSize(455,225);
        setLocationRelativeTo(null);

        textFields = new ArrayList<>();
        textFields.add(nombreField);
        textFields.add(precioField);
        textFields.add(cantField);

    }

    public boolean todosLosCamposLlenos() {
            for(Component component: textFields) {

                if (component instanceof JTextField textField) {
                    if (textField.getText().trim().isEmpty()) {
                        return false;  // Al menos un campo está vacío
                    }
                }
            }
        return true;  // Todos los campos tienen contenido
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
