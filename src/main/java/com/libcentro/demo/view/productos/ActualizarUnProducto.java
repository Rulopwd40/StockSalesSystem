package com.libcentro.demo.view.productos;

import javax.swing.*;
import java.awt.event.*;

public class ActualizarUnProducto extends JDialog {
    private JPanel contentPane;
    private JButton actualizarButton;
    private JButton cerrarButton;
    private JTextField codigoField;
    private JButton buscarButton;
    private JTextField nombreField;
    private JComboBox categoriaBox;
    private JTextField precioVentaField;
    private JTextField costoCompraField;
    private JTextField stockField;

    public ActualizarUnProducto() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(actualizarButton);
        setTitle("Actualizar Un Producto");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        setSize(600,330);
        setLocationRelativeTo(null);
        actualizarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        cerrarButton.addActionListener(new ActionListener() {
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
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public JTextField getStockField() {
        return stockField;
    }

    public JTextField getCostoCompraField() {
        return costoCompraField;
    }

    public JTextField getPrecioVentaField() {
        return precioVentaField;
    }

    public JComboBox getCategoriaBox() {
        return categoriaBox;
    }

    public JTextField getNombreField() {
        return nombreField;
    }

    public JButton getBuscarButton() {
        return buscarButton;
    }

    public JTextField getCodigoField() {
        return codigoField;
    }

    public JButton getCerrarButton() {
        return cerrarButton;
    }

    public JButton getActualizarButton() {
        return actualizarButton;
    }
}
