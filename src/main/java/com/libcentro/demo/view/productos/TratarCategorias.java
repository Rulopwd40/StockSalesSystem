package com.libcentro.demo.view.productos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class TratarCategorias extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable tablaCategorias;
    private JButton crearButton;
    private JButton elegirOtraCategoriaButton;
    private JButton anularButton;
    private JPanel optionalPane;
    private JButton elegirButton;
    private JTable tablaCategoriasExistentes;

    public TratarCategorias (){
        setContentPane (contentPane);
        setModal (true);
        getRootPane ().setDefaultButton (buttonOK);
        setSize (new Dimension (450, 300));
        setLocationRelativeTo (null);
        createTable ();
        setUndecorated (true);
    }

    private void createTable (){
        tablaCategorias.setModel (new DefaultTableModel (null, new String[]{"Categoria"}) {
            @Override
            public boolean isCellEditable ( int row, int column ){
                return false;
            }
        });
        tablaCategoriasExistentes.setModel (new DefaultTableModel (null, new String[]{"Categoria"}) {
            @Override
            public boolean isCellEditable ( int row, int column ){
                return false;
            }
        });
    }

    public void onOK (){
        // add your code here
        dispose ();
    }


    public JButton getAnularButton (){
        return anularButton;
    }

    public JButton getElegirOtraCategoriaButton (){
        return elegirOtraCategoriaButton;
    }

    public JButton getCrearButton (){
        return crearButton;
    }

    public JTable getTablaCategorias (){
        return tablaCategorias;
    }

    public JTable getTablaCategoriasExistentes (){
        return tablaCategoriasExistentes;
    }

    public JButton getElegirButton (){
        return elegirButton;
    }

    public JPanel getOptionalPane (){
        return optionalPane;
    }

    public JButton getButtonOK (){
        return buttonOK;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$ ();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$ (){
        contentPane = new JPanel ();
        contentPane.setLayout (new com.intellij.uiDesigner.core.GridLayoutManager (3, 1, new Insets (10, 10, 10, 10), -1, -1));
        contentPane.setBackground (new Color (-1));
        final JPanel panel1 = new JPanel ();
        panel1.setLayout (new com.intellij.uiDesigner.core.GridLayoutManager (1, 2, new Insets (0, 0, 0, 0), -1, -1));
        panel1.setBackground (new Color (-1));
        contentPane.add (panel1, new com.intellij.uiDesigner.core.GridConstraints (2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer ();
        panel1.add (spacer1, new com.intellij.uiDesigner.core.GridConstraints (0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel ();
        panel2.setLayout (new com.intellij.uiDesigner.core.GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel2.setBackground (new Color (-1));
        panel1.add (panel2, new com.intellij.uiDesigner.core.GridConstraints (0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton ();
        buttonOK.setBackground (new Color (-12406529));
        buttonOK.setForeground (new Color (-1));
        buttonOK.setText ("OK");
        panel2.add (buttonOK, new com.intellij.uiDesigner.core.GridConstraints (0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel ();
        panel3.setLayout (new com.intellij.uiDesigner.core.GridLayoutManager (2, 3, new Insets (0, 0, 0, 0), -1, -1));
        panel3.setBackground (new Color (-1));
        contentPane.add (panel3, new com.intellij.uiDesigner.core.GridConstraints (1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane ();
        scrollPane1.setBackground (new Color (-1));
        panel3.add (scrollPane1, new com.intellij.uiDesigner.core.GridConstraints (0, 0, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tablaCategorias = new JTable ();
        scrollPane1.setViewportView (tablaCategorias);
        final JPanel panel4 = new JPanel ();
        panel4.setLayout (new com.intellij.uiDesigner.core.GridLayoutManager (3, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel4.setBackground (new Color (-1));
        panel3.add (panel4, new com.intellij.uiDesigner.core.GridConstraints (0, 1, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        crearButton = new JButton ();
        crearButton.setBackground (new Color (-12406529));
        crearButton.setForeground (new Color (-1));
        crearButton.setHideActionText (false);
        crearButton.setText ("Crear");
        panel4.add (crearButton, new com.intellij.uiDesigner.core.GridConstraints (0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        elegirOtraCategoriaButton = new JButton ();
        elegirOtraCategoriaButton.setBackground (new Color (-12406529));
        elegirOtraCategoriaButton.setForeground (new Color (-1));
        elegirOtraCategoriaButton.setHideActionText (false);
        elegirOtraCategoriaButton.setText ("Elegir otra categoria");
        panel4.add (elegirOtraCategoriaButton, new com.intellij.uiDesigner.core.GridConstraints (1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        anularButton = new JButton ();
        anularButton.setBackground (new Color (-12406529));
        anularButton.setForeground (new Color (-1));
        anularButton.setHideActionText (false);
        anularButton.setText ("Anular");
        panel4.add (anularButton, new com.intellij.uiDesigner.core.GridConstraints (2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        optionalPane = new JPanel ();
        optionalPane.setLayout (new com.intellij.uiDesigner.core.GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        optionalPane.setEnabled (false);
        panel3.add (optionalPane, new com.intellij.uiDesigner.core.GridConstraints (0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tablaCategoriasExistentes = new JTable ();
        tablaCategoriasExistentes.setEnabled (false);
        optionalPane.add (tablaCategoriasExistentes, new com.intellij.uiDesigner.core.GridConstraints (0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension (150, 50), null, 0, false));
        elegirButton = new JButton ();
        elegirButton.setBackground (new Color (-12406529));
        elegirButton.setEnabled (false);
        elegirButton.setForeground (new Color (-1));
        elegirButton.setText ("Elegir");
        panel3.add (elegirButton, new com.intellij.uiDesigner.core.GridConstraints (1, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel ();
        label1.setForeground (new Color (-16777216));
        label1.setText ("Tratar categorias que no existen");
        contentPane.add (label1, new com.intellij.uiDesigner.core.GridConstraints (0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$ (){
        return contentPane;
    }
}
