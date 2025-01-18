package com.libcentro.demo.view.productos;

import javax.swing.*;
import java.awt.event.*;

public class ActualizarPorCategoria extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox categoriaBox;
    private JButton actualizarButton;
    private JTextField porcentajeField;
    private JPanel categoriaPane;

    public ActualizarPorCategoria() {

    }

    public void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void initialize(){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(420,200);
        setLocationRelativeTo(null);

    }

    public void addListeners(){
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

    public JTextField getPorcentajeField() {
        return porcentajeField;
    }

    public JButton getActualizarButton() {
        return actualizarButton;
    }

    public JComboBox getCategoriaBox() {
        return categoriaBox;
    }

    public JButton getButtonOK() {
        return buttonOK;
    }
}
