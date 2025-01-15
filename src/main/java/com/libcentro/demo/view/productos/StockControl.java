package com.libcentro.demo.view.productos;

import javax.swing.*;
import java.awt.*;

public class StockControl extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTable table1;

    public StockControl() {
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        requestFocus(true);
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize = getSize();

        int x = (int) (Math.round(screenSize.width * 0.90) - windowSize.width);
        int y = (screenSize.height - windowSize.height) / 2;

        setLocation(x, y);
    }

    public JTable getTable() {
        return table1;
    }

    public JButton getButtonOK() {
        return buttonOK;
    }


}
