package com.libcentro.demo.view.productos;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

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

    public ActualizarUnProducto (){
        setContentPane (contentPane);
        setModal (true);
        getRootPane ().setDefaultButton (buscarButton);
        setTitle ("Actualizar Un Producto");
        setDefaultCloseOperation (DO_NOTHING_ON_CLOSE);
        addWindowListener (new WindowAdapter () {
            public void windowClosing ( WindowEvent e ){
                onCancel ();
            }
        });
        setSize (600, 330);
        setLocationRelativeTo (null);


    }


    public void onCancel (){
        // add your code here if necessary
        dispose ();
    }

    public JTextField getStockField (){
        return stockField;
    }

    public JTextField getCostoCompraField (){
        return costoCompraField;
    }

    public JTextField getPrecioVentaField (){
        return precioVentaField;
    }

    public JComboBox getCategoriaBox (){
        return categoriaBox;
    }

    public JTextField getNombreField (){
        return nombreField;
    }

    public JButton getBuscarButton (){
        return buscarButton;
    }

    public JTextField getCodigoField (){
        return codigoField;
    }

    public JButton getCerrarButton (){
        return cerrarButton;
    }

    public JButton getActualizarButton (){
        return actualizarButton;
    }

    @Override
    public JPanel getContentPane (){
        return contentPane;
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
        panel2.setLayout (new com.intellij.uiDesigner.core.GridLayoutManager (1, 2, new Insets (0, 0, 0, 0), -1, -1, true, false));
        panel2.setBackground (new Color (-1));
        panel1.add (panel2, new com.intellij.uiDesigner.core.GridConstraints (0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        actualizarButton = new JButton ();
        actualizarButton.setBackground (new Color (-12406529));
        actualizarButton.setForeground (new Color (-1));
        actualizarButton.setText ("Actualizar");
        panel2.add (actualizarButton, new com.intellij.uiDesigner.core.GridConstraints (0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cerrarButton = new JButton ();
        cerrarButton.setBackground (new Color (-1));
        cerrarButton.setForeground (new Color (-12406529));
        cerrarButton.setText ("Cerrar");
        panel2.add (cerrarButton, new com.intellij.uiDesigner.core.GridConstraints (0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel ();
        panel3.setLayout (new com.intellij.uiDesigner.core.GridLayoutManager (5, 4, new Insets (0, 0, 0, 0), -1, -1));
        panel3.setBackground (new Color (-1));
        contentPane.add (panel3, new com.intellij.uiDesigner.core.GridConstraints (1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel ();
        panel4.setLayout (new com.intellij.uiDesigner.core.GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel4.setBackground (new Color (-1));
        panel3.add (panel4, new com.intellij.uiDesigner.core.GridConstraints (0, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), "Codigo:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        codigoField = new JTextField ();
        codigoField.setBackground (new Color (-1));
        panel4.add (codigoField, new com.intellij.uiDesigner.core.GridConstraints (0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension (150, -1), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer ();
        panel3.add (spacer2, new com.intellij.uiDesigner.core.GridConstraints (4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel5 = new JPanel ();
        panel5.setLayout (new com.intellij.uiDesigner.core.GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel5.setBackground (new Color (-1));
        panel3.add (panel5, new com.intellij.uiDesigner.core.GridConstraints (1, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel5.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), "Nombre:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        nombreField = new JTextField ();
        nombreField.setBackground (new Color (-1));
        panel5.add (nombreField, new com.intellij.uiDesigner.core.GridConstraints (0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension (150, -1), null, 0, false));
        final JPanel panel6 = new JPanel ();
        panel6.setLayout (new com.intellij.uiDesigner.core.GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel6.setBackground (new Color (-1));
        panel3.add (panel6, new com.intellij.uiDesigner.core.GridConstraints (2, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel6.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), "Categoria:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        categoriaBox = new JComboBox ();
        categoriaBox.setBackground (new Color (-1));
        categoriaBox.setFocusable (false);
        panel6.add (categoriaBox, new com.intellij.uiDesigner.core.GridConstraints (0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel ();
        panel7.setLayout (new com.intellij.uiDesigner.core.GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel7.setBackground (new Color (-1));
        panel3.add (panel7, new com.intellij.uiDesigner.core.GridConstraints (3, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel7.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), "Precio Venta:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        precioVentaField = new JTextField ();
        precioVentaField.setBackground (new Color (-1));
        precioVentaField.setText ("");
        panel7.add (precioVentaField, new com.intellij.uiDesigner.core.GridConstraints (0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension (150, -1), null, 0, false));
        final JPanel panel8 = new JPanel ();
        panel8.setLayout (new com.intellij.uiDesigner.core.GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel8.setBackground (new Color (-1));
        panel3.add (panel8, new com.intellij.uiDesigner.core.GridConstraints (3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel8.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), "Costo Compra:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        costoCompraField = new JTextField ();
        costoCompraField.setBackground (new Color (-1));
        costoCompraField.setText ("");
        panel8.add (costoCompraField, new com.intellij.uiDesigner.core.GridConstraints (0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension (150, -1), null, 0, false));
        final JPanel panel9 = new JPanel ();
        panel9.setLayout (new com.intellij.uiDesigner.core.GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel9.setBackground (new Color (-1));
        panel3.add (panel9, new com.intellij.uiDesigner.core.GridConstraints (3, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel9.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), "Stock:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        stockField = new JTextField ();
        stockField.setBackground (new Color (-1));
        panel9.add (stockField, new com.intellij.uiDesigner.core.GridConstraints (0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension (150, -1), null, 0, false));
        final JPanel panel10 = new JPanel ();
        panel10.setLayout (new com.intellij.uiDesigner.core.GridLayoutManager (1, 1, new Insets (15, 0, 0, 0), -1, -1));
        panel10.setBackground (new Color (-1));
        panel3.add (panel10, new com.intellij.uiDesigner.core.GridConstraints (0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel10.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        buscarButton = new JButton ();
        buscarButton.setBackground (new Color (-12406529));
        buscarButton.setForeground (new Color (-1));
        buscarButton.setText ("Buscar");
        panel10.add (buscarButton, new com.intellij.uiDesigner.core.GridConstraints (0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel ();
        label1.setEnabled (true);
        Font label1Font = this.$$$getFont$$$ (null, -1, 20, label1.getFont ());
        if ( label1Font != null ) label1.setFont (label1Font);
        label1.setText ("Actualizar un producto");
        contentPane.add (label1, new com.intellij.uiDesigner.core.GridConstraints (0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$ ( String fontName, int style, int size, Font currentFont ){
        if ( currentFont == null ) return null;
        String resultName;
        if ( fontName == null ) {
            resultName = currentFont.getName ();
        } else {
            Font testFont = new Font (fontName, Font.PLAIN, 10);
            if ( testFont.canDisplay ('a') && testFont.canDisplay ('1') ) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName ();
            }
        }
        Font font = new Font (resultName, style >= 0 ? style : currentFont.getStyle (), size >= 0 ? size : currentFont.getSize ());
        boolean isMac = System.getProperty ("os.name", "").toLowerCase (Locale.ENGLISH).startsWith ("mac");
        Font fontWithFallback = isMac ? new Font (font.getFamily (), font.getStyle (), font.getSize ()) : new StyleContext ().getFont (font.getFamily (), font.getStyle (), font.getSize ());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource (fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$ (){
        return contentPane;
    }
}
