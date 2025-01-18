package com.libcentro.demo.view;

import javax.swing.*;
import java.awt.event.*;

public class ConfirmarDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel messageLabel;
    private boolean aceptar=false;
    private final String message;

    public ConfirmarDialog(String message) {
        this.message = message;
    }

    public void initialize(){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        messageLabel.setText(message);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);
        addListeners();
    }

    private void addListeners (){
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

    }

    private void onOK() {
        aceptar=true;
        dispose();
    }

    private void onCancel() {
        aceptar=false;
        dispose();
    }

    public boolean isAceptar() {
        return aceptar;
    }
}
