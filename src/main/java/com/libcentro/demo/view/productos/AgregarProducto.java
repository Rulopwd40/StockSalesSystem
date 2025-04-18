package com.libcentro.demo.view.productos;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.libcentro.demo.utils.filters.Filter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

public class AgregarProducto extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField codigoField;
    private JTextField nombreField;
    private JTextField precioField;
    private JComboBox<String> categoriaBox;
    private JTextField costoField;
    private JTextField cantidadField;
    private JPanel errorPanel;

    public AgregarProducto (){
        setContentPane (contentPane);
        setModal (true);
        getRootPane ().setDefaultButton (buttonOK);
        setSize (600, 325);
        setLocationRelativeTo (null);

        setFilters ();


        buttonCancel.addActionListener (new ActionListener () {
            public void actionPerformed ( ActionEvent e ){
                onCancel ();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation (DO_NOTHING_ON_CLOSE);
        addWindowListener (new WindowAdapter () {
            public void windowClosing ( WindowEvent e ){
                onCancel ();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction (new ActionListener () {
            public void actionPerformed ( ActionEvent e ){
                onCancel ();
            }
        }, KeyStroke.getKeyStroke (KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void setFilters (){
        Filter.setIntegerFilter (codigoField);
        Filter.setSymbolFilter (nombreField);
        Filter.setPrecioFilter (precioField);
        Filter.setPrecioFilter (costoField);
        Filter.setIntegerFilter (cantidadField);
    }

    public void onOK (){
        // add your code here
        dispose ();
    }

    public void onCancel (){
        // add your code here if necessary
        dispose ();
    }

    public JTextField getCantidadField (){
        return cantidadField;
    }

    public JTextField getCostoField (){
        return costoField;
    }

    public JComboBox<String> getCategoriaBox (){
        return categoriaBox;
    }

    public JTextField getPrecioField (){
        return precioField;
    }

    public JTextField getNombreField (){
        return nombreField;
    }

    public JTextField getCodigoField (){
        return codigoField;
    }

    public JButton getButtonOK (){
        return buttonOK;
    }

    public JPanel getErrorPanel (){
        return errorPanel;
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
        contentPane.setLayout (new GridLayoutManager (6, 2, new Insets (10, 10, 10, 10), -1, -1));
        contentPane.setBackground (new Color (-1));
        final JPanel panel1 = new JPanel ();
        panel1.setLayout (new GridLayoutManager (1, 3, new Insets (0, 0, 0, 0), -1, -1));
        panel1.setBackground (new Color (-1));
        contentPane.add (panel1, new GridConstraints (5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel1.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel2 = new JPanel ();
        panel2.setLayout (new GridLayoutManager (1, 2, new Insets (0, 0, 0, 0), -1, -1, true, false));
        panel2.setBackground (new Color (-1));
        panel1.add (panel2, new GridConstraints (0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton ();
        buttonOK.setBackground (new Color (-12406529));
        buttonOK.setForeground (new Color (-1));
        buttonOK.setText ("OK");
        panel2.add (buttonOK, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton ();
        buttonCancel.setBackground (new Color (-1));
        buttonCancel.setForeground (new Color (-12406529));
        buttonCancel.setText ("Cancel");
        panel2.add (buttonCancel, new GridConstraints (0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        errorPanel = new JPanel ();
        errorPanel.setLayout (new GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        errorPanel.setBackground (new Color (-1));
        panel1.add (errorPanel, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer ();
        panel1.add (spacer1, new GridConstraints (0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel3 = new JPanel ();
        panel3.setLayout (new GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel3.setBackground (new Color (-1));
        contentPane.add (panel3, new GridConstraints (3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), "Categoria:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        categoriaBox = new JComboBox ();
        categoriaBox.setBackground (new Color (-1));
        panel3.add (categoriaBox, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel ();
        panel4.setLayout (new GridLayoutManager (1, 3, new Insets (0, 0, 0, 0), -1, -1));
        panel4.setBackground (new Color (-1));
        contentPane.add (panel4, new GridConstraints (4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel5 = new JPanel ();
        panel5.setLayout (new GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel5.setBackground (new Color (-1));
        panel4.add (panel5, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel5.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), "Precio Unitario", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        precioField = new JTextField ();
        precioField.setBackground (new Color (-1));
        panel5.add (precioField, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension (150, -1), null, 0, false));
        final JPanel panel6 = new JPanel ();
        panel6.setLayout (new GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel6.setBackground (new Color (-1));
        panel4.add (panel6, new GridConstraints (0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel6.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), "Costo Compra:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        costoField = new JTextField ();
        costoField.setBackground (new Color (-1));
        panel6.add (costoField, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension (150, -1), null, 0, false));
        final JPanel panel7 = new JPanel ();
        panel7.setLayout (new GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel7.setBackground (new Color (-1));
        panel4.add (panel7, new GridConstraints (0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel7.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), "Cantidad:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        cantidadField = new JTextField ();
        cantidadField.setBackground (new Color (-1));
        cantidadField.setText ("");
        panel7.add (cantidadField, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension (150, -1), null, 0, false));
        final JPanel panel8 = new JPanel ();
        panel8.setLayout (new GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel8.setBackground (new Color (-1));
        contentPane.add (panel8, new GridConstraints (2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel8.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), "Nombre:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        nombreField = new JTextField ();
        nombreField.setBackground (new Color (-1));
        panel8.add (nombreField, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension (150, -1), null, 0, false));
        final JPanel panel9 = new JPanel ();
        panel9.setLayout (new GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel9.setBackground (new Color (-1));
        contentPane.add (panel9, new GridConstraints (1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel9.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), "Codigo:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        codigoField = new JTextField ();
        codigoField.setBackground (new Color (-1));
        panel9.add (codigoField, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension (150, -1), null, 0, false));
        final JLabel label1 = new JLabel ();
        label1.setBackground (new Color (-1));
        Font label1Font = this.$$$getFont$$$ (null, -1, 18, label1.getFont ());
        if ( label1Font != null ) label1.setFont (label1Font);
        label1.setText ("Agregar Producto");
        contentPane.add (label1, new GridConstraints (0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
