package com.libcentro.demo.view.estadisticas;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class InformacionFrame extends JFrame {
    private JButton buscarButton;
    private JTextField buscarField;
    private JButton mostrarButton;
    private JButton eliminarButton;
    private JButton reembolsarButton;
    private JTable productosTable;
    private JTable ventaTable;
    private JScrollPane JScrollPane;
    private JPanel panel;
    private JButton anteriorButton;
    private JButton siguienteButton;
    private JLabel pageCount;
    private JTable pfsTable;
    private JLabel hora;
    private JButton reembolsarTodoButton;

    public InformacionFrame (){
        super ("Informacion");

        setContentPane (panel);
        setSize (1056, 524);
        setLocationRelativeTo (null);
        setResizable (false);
    }

    public JButton getBuscarButton (){
        return buscarButton;
    }

    public JTextField getBuscarField (){
        return buscarField;
    }

    public JButton getMostrarButton (){
        return mostrarButton;
    }

    public JButton getEliminarButton (){
        return eliminarButton;
    }

    public JButton getReembolsarButton (){
        return reembolsarButton;
    }

    public JTable getProductosTable (){
        return productosTable;
    }

    public JTable getVentaTable (){
        return ventaTable;
    }

    public JButton getAnteriorButton (){
        return anteriorButton;
    }

    public JButton getSiguienteButton (){
        return siguienteButton;
    }

    public JLabel getPageCount (){
        return pageCount;
    }

    public JTable getPfsTable (){
        return pfsTable;
    }

    public JLabel getHora (){
        return hora;
    }

    public JButton getReembolsarTodoButton (){
        return reembolsarTodoButton;
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
        panel = new JPanel ();
        panel.setLayout (new GridLayoutManager (2, 6, new Insets (0, 0, 0, 0), -1, -1));
        panel.setBackground (new Color (-1));
        final JPanel panel1 = new JPanel ();
        panel1.setLayout (new GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel1.setBackground (new Color (-1));
        panel.add (panel1, new GridConstraints (0, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel ();
        label1.setForeground (new Color (-16777216));
        label1.setText ("Informacion");
        panel1.add (label1, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel ();
        panel2.setLayout (new GridLayoutManager (1, 3, new Insets (0, 0, 0, 0), -1, -1));
        panel2.setBackground (new Color (-1));
        panel.add (panel2, new GridConstraints (1, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel ();
        panel3.setLayout (new GridLayoutManager (3, 3, new Insets (0, 5, 5, 0), -1, -1));
        panel3.setBackground (new Color (-1));
        panel2.add (panel3, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        JScrollPane = new JScrollPane ();
        JScrollPane.setBackground (new Color (-1));
        panel3.add (JScrollPane, new GridConstraints (1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        JScrollPane.setBorder (BorderFactory.createTitledBorder (null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        ventaTable = new JTable ();
        JScrollPane.setViewportView (ventaTable);
        final JPanel panel4 = new JPanel ();
        panel4.setLayout (new GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel4.setBackground (new Color (-1));
        panel4.setForeground (new Color (-16777216));
        panel3.add (panel4, new GridConstraints (0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.setBorder (BorderFactory.createTitledBorder (null, "Buscar:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        buscarField = new JTextField ();
        buscarField.setBackground (new Color (-1));
        panel4.add (buscarField, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension (150, -1), null, 0, false));
        anteriorButton = new JButton ();
        anteriorButton.setBackground (new Color (-12406529));
        anteriorButton.setForeground (new Color (-1));
        anteriorButton.setText ("Anterior");
        panel3.add (anteriorButton, new GridConstraints (2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        siguienteButton = new JButton ();
        siguienteButton.setBackground (new Color (-12406529));
        siguienteButton.setForeground (new Color (-1));
        siguienteButton.setText ("Siguiente");
        panel3.add (siguienteButton, new GridConstraints (2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pageCount = new JLabel ();
        pageCount.setForeground (new Color (-16777216));
        pageCount.setText ("0");
        panel3.add (pageCount, new GridConstraints (2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel ();
        panel5.setLayout (new GridLayoutManager (4, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel5.setBackground (new Color (-1));
        panel2.add (panel5, new GridConstraints (0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        eliminarButton = new JButton ();
        eliminarButton.setBackground (new Color (-3332066));
        eliminarButton.setForeground (new Color (-1));
        eliminarButton.setText ("Deshabilitar");
        panel5.add (eliminarButton, new GridConstraints (2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mostrarButton = new JButton ();
        mostrarButton.setBackground (new Color (-12406529));
        mostrarButton.setForeground (new Color (-1));
        mostrarButton.setText ("Mostrar");
        panel5.add (mostrarButton, new GridConstraints (1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer ();
        panel5.add (spacer1, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer ();
        panel5.add (spacer2, new GridConstraints (3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel6 = new JPanel ();
        panel6.setLayout (new GridLayoutManager (3, 1, new Insets (0, 0, 5, 5), -1, -1));
        panel6.setBackground (new Color (-1));
        panel2.add (panel6, new GridConstraints (0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel7 = new JPanel ();
        panel7.setLayout (new GridLayoutManager (4, 2, new Insets (0, 0, 0, 0), -1, -1));
        panel7.setBackground (new Color (-1));
        panel6.add (panel7, new GridConstraints (2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        reembolsarButton = new JButton ();
        reembolsarButton.setBackground (new Color (-12406529));
        reembolsarButton.setForeground (new Color (-1));
        reembolsarButton.setText ("Reembolsar Seleccionado");
        panel7.add (reembolsarButton, new GridConstraints (3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final javax.swing.JScrollPane scrollPane1 = new JScrollPane ();
        scrollPane1.setBackground (new Color (-1));
        panel7.add (scrollPane1, new GridConstraints (0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        productosTable = new JTable ();
        productosTable.setBackground (new Color (-1));
        scrollPane1.setViewportView (productosTable);
        final javax.swing.JScrollPane scrollPane2 = new JScrollPane ();
        scrollPane2.setBackground (new Color (-1));
        panel7.add (scrollPane2, new GridConstraints (2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        pfsTable = new JTable ();
        pfsTable.setBackground (new Color (-1));
        scrollPane2.setViewportView (pfsTable);
        final JPanel panel8 = new JPanel ();
        panel8.setLayout (new GridLayoutManager (1, 2, new Insets (0, 0, 0, 0), -1, -1));
        panel8.setBackground (new Color (-1));
        panel7.add (panel8, new GridConstraints (1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel ();
        label2.setForeground (new Color (-16777216));
        label2.setText ("Productos fuera de stock:");
        panel8.add (label2, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer ();
        panel8.add (spacer3, new GridConstraints (0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        reembolsarTodoButton = new JButton ();
        reembolsarTodoButton.setBackground (new Color (-12406529));
        reembolsarTodoButton.setForeground (new Color (-1));
        reembolsarTodoButton.setText ("Reembolsar Todo");
        panel7.add (reembolsarTodoButton, new GridConstraints (3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel9 = new JPanel ();
        panel9.setLayout (new GridLayoutManager (1, 2, new Insets (0, 0, 0, 0), -1, -1));
        panel9.setBackground (new Color (-1));
        panel6.add (panel9, new GridConstraints (1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel ();
        label3.setForeground (new Color (-16777216));
        label3.setText ("Productos:");
        panel9.add (label3, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer ();
        panel9.add (spacer4, new GridConstraints (0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel10 = new JPanel ();
        panel10.setLayout (new GridLayoutManager (1, 2, new Insets (0, 0, 0, 0), -1, -1));
        panel10.setBackground (new Color (-1));
        panel6.add (panel10, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        hora = new JLabel ();
        hora.setForeground (new Color (-16776701));
        hora.setText ("");
        panel10.add (hora, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer ();
        panel10.add (spacer5, new GridConstraints (0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$ (){
        return panel;
    }

}
