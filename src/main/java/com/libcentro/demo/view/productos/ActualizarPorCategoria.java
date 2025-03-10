package com.libcentro.demo.view.productos;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

public class ActualizarPorCategoria extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox categoriaBox;
    private JButton actualizarButton;
    private JTextField porcentajeField;
    private JPanel categoriaPane;

    public ActualizarPorCategoria (){
        setContentPane (contentPane);
        setModal (true);
        getRootPane ().setDefaultButton (buttonOK);
        setSize (420, 200);
        setLocationRelativeTo (null);


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

    public void onOK (){
        // add your code here
        dispose ();
    }

    private void onCancel (){
        // add your code here if necessary
        dispose ();
    }

    public JTextField getPorcentajeField (){
        return porcentajeField;
    }

    public JButton getActualizarButton (){
        return actualizarButton;
    }

    public JComboBox getCategoriaBox (){
        return categoriaBox;
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
        contentPane.setLayout (new GridLayoutManager (3, 1, new Insets (10, 10, 10, 10), -1, -1));
        contentPane.setBackground (new Color (-1));
        final JPanel panel1 = new JPanel ();
        panel1.setLayout (new GridLayoutManager (1, 2, new Insets (0, 0, 0, 0), -1, -1));
        panel1.setBackground (new Color (-1));
        contentPane.add (panel1, new GridConstraints (2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer ();
        panel1.add (spacer1, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel ();
        panel2.setLayout (new GridLayoutManager (1, 2, new Insets (0, 0, 0, 0), -1, -1, true, false));
        panel2.setBackground (new Color (-1));
        panel1.add (panel2, new GridConstraints (0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
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
        final JPanel panel3 = new JPanel ();
        panel3.setLayout (new GridLayoutManager (2, 2, new Insets (0, 50, 0, 50), -1, -1));
        panel3.setBackground (new Color (-1));
        contentPane.add (panel3, new GridConstraints (1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel ();
        panel4.setLayout (new GridLayoutManager (1, 2, new Insets (0, 0, 0, 0), -1, -1));
        panel4.setBackground (new Color (-1));
        panel4.setEnabled (true);
        panel3.add (panel4, new GridConstraints (0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel5 = new JPanel ();
        panel5.setLayout (new GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel5.setBackground (new Color (-1));
        panel4.add (panel5, new GridConstraints (0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel5.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), "Variación(%)", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        porcentajeField = new JTextField ();
        porcentajeField.setBackground (new Color (-1));
        panel5.add (porcentajeField, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension (50, -1), null, 0, false));
        categoriaPane = new JPanel ();
        categoriaPane.setLayout (new GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        categoriaPane.setBackground (new Color (-1));
        panel4.add (categoriaPane, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        categoriaPane.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), "Categoria:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        categoriaBox = new JComboBox ();
        categoriaBox.setBackground (new Color (-1));
        categoriaBox.setEditable (false);
        categoriaBox.setFocusable (false);
        categoriaBox.setForeground (new Color (-16777216));
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel ();
        categoriaBox.setModel (defaultComboBoxModel1);
        categoriaPane.add (categoriaBox, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension (150, -1), null, 0, false));
        actualizarButton = new JButton ();
        actualizarButton.setBackground (new Color (-12406529));
        actualizarButton.setForeground (new Color (-1));
        actualizarButton.setText ("Actualizar");
        panel3.add (actualizarButton, new GridConstraints (1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel ();
        Font label1Font = this.$$$getFont$$$ (null, -1, 14, label1.getFont ());
        if ( label1Font != null ) label1.setFont (label1Font);
        label1.setForeground (new Color (-16777216));
        label1.setText ("Actualizar Producto por Categoria");
        contentPane.add (label1, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
