package com.libcentro.demo.view.productos;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

public class AgregarCategoria extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField categoriaField;
    private JTable tablaCategorias;
    private JButton agregarButton;
    private JButton eliminarButton;

    public AgregarCategoria (){
        setContentPane (contentPane);
        setModal (true);
        getRootPane ().setDefaultButton (buttonOK);
        setSize (600, 400);
        createTable ();

        buttonOK.addActionListener (new ActionListener () {
            public void actionPerformed ( ActionEvent e ){
                onOK ();
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

        setLocationRelativeTo (null);
    }

    private void createTable (){
        tablaCategorias.setModel (new DefaultTableModel (
                null,
                new String[]{"Cantidad"}
        ) {
            @Override
            public boolean isCellEditable ( int row, int column ){
                // Aquí defines qué columnas son editables
                switch (column) {
                    case 0:
                        return true;
                    default:
                        return false; // Las demás columnas no son editables
                }
            }
        });
        tablaCategorias.getTableHeader ().setReorderingAllowed (false);
        tablaCategorias.setAutoCreateRowSorter (true);
    }

    private void onOK (){
        // add your code here
        dispose ();
    }

    private void onCancel (){
        // add your code here if necessary
        dispose ();
    }

    public JButton getButtonOK (){
        return buttonOK;
    }


    public JTextField getCategoriaField (){
        return categoriaField;
    }

    public JTable getTablaCategorias (){
        return tablaCategorias;
    }

    public JButton getAgregarButton (){
        return agregarButton;
    }

    public JButton getEliminarButton (){
        return eliminarButton;
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
        contentPane.setLayout (new GridLayoutManager (3, 3, new Insets (10, 10, 10, 10), -1, -1));
        contentPane.setBackground (new Color (-1));
        final JPanel panel1 = new JPanel ();
        panel1.setLayout (new GridLayoutManager (1, 2, new Insets (0, 0, 0, 0), -1, -1));
        contentPane.add (panel1, new GridConstraints (2, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel ();
        panel2.setLayout (new GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel2.setBackground (new Color (-1));
        panel1.add (panel2, new GridConstraints (0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton ();
        buttonOK.setBackground (new Color (-12406529));
        buttonOK.setForeground (new Color (-1));
        buttonOK.setText ("OK");
        panel2.add (buttonOK, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel ();
        panel3.setLayout (new GridLayoutManager (1, 2, new Insets (100, 0, 100, 10), -1, -1));
        panel3.setBackground (new Color (-1));
        contentPane.add (panel3, new GridConstraints (1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel ();
        panel4.setLayout (new GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel4.setBackground (new Color (-1));
        panel3.add (panel4, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), "Categoria:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        categoriaField = new JTextField ();
        categoriaField.setBackground (new Color (-1));
        panel4.add (categoriaField, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension (150, -1), null, 0, false));
        final JPanel panel5 = new JPanel ();
        panel5.setLayout (new GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel3.add (panel5, new GridConstraints (0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        agregarButton = new JButton ();
        agregarButton.setBackground (new Color (-12406529));
        agregarButton.setEnabled (false);
        agregarButton.setForeground (new Color (-1));
        agregarButton.setText ("Agregar");
        panel5.add (agregarButton, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane ();
        scrollPane1.setBackground (new Color (-1));
        scrollPane1.setForeground (new Color (-16777216));
        contentPane.add (scrollPane1, new GridConstraints (1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tablaCategorias = new JTable ();
        scrollPane1.setViewportView (tablaCategorias);
        final JLabel label1 = new JLabel ();
        Font label1Font = this.$$$getFont$$$ (null, -1, 18, label1.getFont ());
        if ( label1Font != null ) label1.setFont (label1Font);
        label1.setText ("Agregar Categoría");
        contentPane.add (label1, new GridConstraints (0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        eliminarButton = new JButton ();
        eliminarButton.setBackground (new Color (-12406529));
        eliminarButton.setEnabled (false);
        eliminarButton.setForeground (new Color (-1));
        eliminarButton.setHideActionText (false);
        eliminarButton.setText ("Eliminar");
        contentPane.add (eliminarButton, new GridConstraints (1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
