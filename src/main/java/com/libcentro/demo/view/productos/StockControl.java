package com.libcentro.demo.view.productos;

import javax.swing.*;

public class StockControl extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTable table1;

    public StockControl() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        pack();
        setLocationRelativeTo(null);
    }

    public JTable getTable() {
        return table1;
    }

    public JButton getButtonOK() {
        return buttonOK;
    }


}
