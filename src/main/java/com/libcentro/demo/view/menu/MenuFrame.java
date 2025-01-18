package com.libcentro.demo.view.menu;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

public class MenuFrame extends JFrame {
    private JButton productosButton;
    private JButton ventaButton;
    private JButton reportesButton;
    private JPanel panelMenu;
    private JPanel menuSub;

    private JPanel ButtonContainer0;
    private JPanel ButtonContainer1;
    private JPanel ButtonContainer2;
    private JLabel Menu;
    private JButton backUpButton;
    private JButton configuracionButton;

    public MenuFrame (){
        setContentPane (panelMenu);
        setFocusable (true);
        setTitle ("Menu");
        setSize (600, 400);
        setDefaultCloseOperation (DISPOSE_ON_CLOSE);
        setLocationRelativeTo (null);
    }


    public JButton getProductosButton (){
        return productosButton;
    }

    public JButton getVentaButton (){
        return ventaButton;
    }

    public JButton getReportesButton (){
        return reportesButton;
    }

    public JButton getBackUpButton (){
        return backUpButton;
    }

    public JButton getConfiguracionButton (){
        return configuracionButton;
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
        final JPanel panel1 = new JPanel ();
        panel1.setLayout (new GridLayoutManager (3, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel1.setBackground (new Color (-1));
        panelMenu = new JPanel ();
        panelMenu.setLayout (new GridLayoutManager (2, 1, new Insets (0, 0, 0, 0), -1, -1));
        panelMenu.setBackground (new Color (-1));
        panelMenu.setEnabled (true);
        panel1.add (panelMenu, new GridConstraints (0, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        menuSub = new JPanel ();
        menuSub.setLayout (new GridLayoutManager (3, 5, new Insets (20, 40, 20, 40), -1, -1));
        menuSub.setBackground (new Color (-1));
        menuSub.setEnabled (true);
        menuSub.putClientProperty ("html.disable", Boolean.FALSE);
        panelMenu.add (menuSub, new GridConstraints (1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        menuSub.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        ButtonContainer0 = new JPanel ();
        ButtonContainer0.setLayout (new GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        menuSub.add (ButtonContainer0, new GridConstraints (1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        ButtonContainer0.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        productosButton = new JButton ();
        productosButton.setBackground (new Color (-12406529));
        productosButton.setEnabled (true);
        productosButton.setForeground (new Color (-2306));
        productosButton.setText ("Productos");
        ButtonContainer0.add (productosButton, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ButtonContainer1 = new JPanel ();
        ButtonContainer1.setLayout (new GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        menuSub.add (ButtonContainer1, new GridConstraints (1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        ButtonContainer1.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        ventaButton = new JButton ();
        ventaButton.setBackground (new Color (-12406529));
        ventaButton.setEnabled (true);
        ventaButton.setForeground (new Color (-2306));
        ventaButton.setText ("Venta(F1)");
        ButtonContainer1.add (ventaButton, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ButtonContainer2 = new JPanel ();
        ButtonContainer2.setLayout (new GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        menuSub.add (ButtonContainer2, new GridConstraints (1, 3, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        ButtonContainer2.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        reportesButton = new JButton ();
        reportesButton.setBackground (new Color (-12406529));
        reportesButton.setEnabled (true);
        reportesButton.setForeground (new Color (-2306));
        reportesButton.setText ("Reportes");
        ButtonContainer2.add (reportesButton, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Menu = new JLabel ();
        Menu.setBackground (new Color (-1));
        Font MenuFont = this.$$$getFont$$$ (null, -1, 22, Menu.getFont ());
        if ( MenuFont != null ) Menu.setFont (MenuFont);
        Menu.setForeground (new Color (-16777216));
        Menu.setText ("Menu");
        menuSub.add (Menu, new GridConstraints (0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel ();
        panel2.setLayout (new GridLayoutManager (1, 2, new Insets (0, 0, 0, 0), -1, -1));
        panel2.setBackground (new Color (-1));
        menuSub.add (panel2, new GridConstraints (2, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        backUpButton = new JButton ();
        backUpButton.setBackground (new Color (-12406529));
        backUpButton.setForeground (new Color (-1));
        backUpButton.setText ("Back-Up");
        panel2.add (backUpButton, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        configuracionButton = new JButton ();
        configuracionButton.setBackground (new Color (-12406529));
        configuracionButton.setForeground (new Color (-1));
        configuracionButton.setText ("Configuracion");
        panel2.add (configuracionButton, new GridConstraints (0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer ();
        menuSub.add (spacer1, new GridConstraints (1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
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

}
