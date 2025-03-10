package com.libcentro.demo.view.productos;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

public class ProductosFrame extends JFrame {
    private JTable table;
    private JTextField buscarField;
    private JCheckBox sinStockCheckBox;
    private JButton agregarCategoriaButton;
    private JButton unProductoButton;
    private JButton importarCsvButton;
    private JButton actualizarUnProductoButton;
    private JButton porCategoriaButton;
    private JButton generalButton;
    private JButton guardarButton;
    private JButton deshacerTodoButton;
    private JButton deshacerCambiosButton;
    private JPanel panelProducto;
    private JButton eliminarProductoButton;
    private JPanel panelBusqueda;
    private JPanel panelAdd;
    private JPanel panelUpd;
    private JPanel panelClose;
    private JButton actualizarTablaButton;
    private JButton siguienteButton;
    private JButton anteriorButton;
    private JLabel pageCount;
    private JScrollPane scrollPane;
    private JCheckBox categoriaCheckbox;
    private JButton productosSeleccionadosButton;


    public ProductosFrame (){
        setContentPane (panelProducto);
        setSize (945, 630);
        setLocationRelativeTo (null);
        createTable ();
        setFocusable (true);
    }

    private void createTable (){
        table.setModel (new DefaultTableModel (
                null,
                new String[]{"Cod.", "Nombre", "Categoria", "Cantidad", "Costo Compra", "Precio U."}
        ) {
            @Override
            public boolean isCellEditable ( int row, int column ){
                return false; // Las demás columnas no son editables
            }/*
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0: // Cod
                        return String.class;
                    case 1:
                        return String.class; // o Double.class si manejas cantidades decimales
                    case 2:
                        return String.class; // Descuento en porcentaje como decimal
                    case 3:
                        return Integer.class; // Precio como decimal
                    case 4:
                        return Float.class;
                    default:
                        return Object.class;
                }
            }*/
        });
        table.getTableHeader ().setReorderingAllowed (false);
        table.setAutoCreateRowSorter (true);


    }

    public JButton getAgregarCategoriaButton (){
        return agregarCategoriaButton;
    }

    public JButton getUnProductoButton (){
        return unProductoButton;
    }

    public JButton getImportarCsvButton (){
        return importarCsvButton;
    }

    public JButton getActualizarUnProductoButton (){
        return actualizarUnProductoButton;
    }

    public JButton getPorCategoriaButton (){
        return porCategoriaButton;
    }

    public JButton getGeneralButton (){
        return generalButton;
    }

    public JButton getGuardarButton (){
        return guardarButton;
    }

    public JButton getDeshacerTodoButton (){
        return deshacerTodoButton;
    }

    public JButton getDeshacerCambiosButton (){
        return deshacerCambiosButton;
    }

    public JButton getEliminarProductoButton (){
        return eliminarProductoButton;
    }

    public JTextField getBuscarField (){
        return buscarField;
    }

    public JCheckBox getSinStockCheckBox (){
        return sinStockCheckBox;
    }

    public JTable getTable (){
        return table;
    }

    public JButton getActualizarTablaButton (){
        return actualizarTablaButton;
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

    public JScrollPane getScrollPane (){
        return scrollPane;
    }

    public JCheckBox getCategoriaCheckbox (){
        return categoriaCheckbox;
    }

    public JButton getProductosSeleccionadosButton (){
        return productosSeleccionadosButton;
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
        panelProducto = new JPanel ();
        panelProducto.setLayout (new GridLayoutManager (8, 2, new Insets (0, 0, 0, 0), -1, -1));
        panelProducto.setBackground (new Color (-1));
        final JPanel panel1 = new JPanel ();
        panel1.setLayout (new GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel1.setBackground (new Color (-1));
        panelProducto.add (panel1, new GridConstraints (0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel1.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label1 = new JLabel ();
        label1.setEnabled (true);
        Font label1Font = this.$$$getFont$$$ (null, Font.BOLD, 14, label1.getFont ());
        if ( label1Font != null ) label1.setFont (label1Font);
        label1.setForeground (new Color (-16777216));
        label1.setText ("Productos");
        panel1.add (label1, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPane = new JScrollPane ();
        scrollPane.setBackground (new Color (-1));
        scrollPane.setForeground (new Color (-16777216));
        scrollPane.setVerticalScrollBarPolicy (22);
        panelProducto.add (scrollPane, new GridConstraints (2, 0, 5, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table = new JTable ();
        table.setBackground (new Color (-1));
        table.setForeground (new Color (-16777216));
        table.setGridColor (new Color (-16777216));
        scrollPane.setViewportView (table);
        panelBusqueda = new JPanel ();
        panelBusqueda.setLayout (new GridLayoutManager (1, 4, new Insets (5, 5, 5, 5), -1, -1));
        panelBusqueda.setBackground (new Color (-1));
        panelProducto.add (panelBusqueda, new GridConstraints (1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelBusqueda.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel2 = new JPanel ();
        panel2.setLayout (new GridLayoutManager (2, 2, new Insets (0, 0, 0, 0), -1, -1));
        panel2.setBackground (new Color (-1));
        panel2.setForeground (new Color (-16777216));
        panelBusqueda.add (panel2, new GridConstraints (0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), "Buscar", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        buscarField = new JTextField ();
        panel2.add (buscarField, new GridConstraints (0, 0, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension (250, -1), null, 0, false));
        sinStockCheckBox = new JCheckBox ();
        sinStockCheckBox.setBackground (new Color (-1));
        sinStockCheckBox.setForeground (new Color (-16777216));
        sinStockCheckBox.setText ("Sin Stock");
        panel2.add (sinStockCheckBox, new GridConstraints (0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        categoriaCheckbox = new JCheckBox ();
        categoriaCheckbox.setBackground (new Color (-1));
        categoriaCheckbox.setForeground (new Color (-16777216));
        categoriaCheckbox.setText ("Categoria nula");
        panel2.add (categoriaCheckbox, new GridConstraints (1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel ();
        panel3.setLayout (new GridLayoutManager (1, 1, new Insets (0, 0, 0, 0), -1, -1));
        panel3.setBackground (new Color (-1));
        panelBusqueda.add (panel3, new GridConstraints (0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        agregarCategoriaButton = new JButton ();
        agregarCategoriaButton.setBackground (new Color (-12406529));
        agregarCategoriaButton.setForeground (new Color (-1));
        agregarCategoriaButton.setText ("Agregar Categoria");
        panel3.add (agregarCategoriaButton, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        actualizarTablaButton = new JButton ();
        actualizarTablaButton.setBackground (new Color (-12406529));
        actualizarTablaButton.setForeground (new Color (-1));
        actualizarTablaButton.setText ("Actualizar Tabla");
        panelBusqueda.add (actualizarTablaButton, new GridConstraints (0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelAdd = new JPanel ();
        panelAdd.setLayout (new GridLayoutManager (3, 1, new Insets (5, 5, 5, 5), -1, -1));
        panelAdd.setBackground (new Color (-1));
        panelProducto.add (panelAdd, new GridConstraints (1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelAdd.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label2 = new JLabel ();
        Font label2Font = this.$$$getFont$$$ (null, -1, 18, label2.getFont ());
        if ( label2Font != null ) label2.setFont (label2Font);
        label2.setText ("Agregar Producto");
        panelAdd.add (label2, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        unProductoButton = new JButton ();
        unProductoButton.setBackground (new Color (-12406529));
        unProductoButton.setForeground (new Color (-1));
        unProductoButton.setText ("Un Producto");
        panelAdd.add (unProductoButton, new GridConstraints (1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        importarCsvButton = new JButton ();
        importarCsvButton.setBackground (new Color (-12406529));
        importarCsvButton.setForeground (new Color (-1));
        importarCsvButton.setText ("Importar .csv");
        panelAdd.add (importarCsvButton, new GridConstraints (2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelUpd = new JPanel ();
        panelUpd.setLayout (new GridLayoutManager (2, 1, new Insets (5, 5, 5, 5), -1, -1));
        panelUpd.setBackground (new Color (-1));
        panelProducto.add (panelUpd, new GridConstraints (2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panelUpd.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEmptyBorder (), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label3 = new JLabel ();
        Font label3Font = this.$$$getFont$$$ (null, -1, 18, label3.getFont ());
        if ( label3Font != null ) label3.setFont (label3Font);
        label3.setText ("Actualizar Productos");
        panelUpd.add (label3, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel ();
        panel4.setLayout (new GridLayoutManager (4, 1, new Insets (0, 0, 0, 0), -1, -1));
        panelUpd.add (panel4, new GridConstraints (1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        actualizarUnProductoButton = new JButton ();
        actualizarUnProductoButton.setBackground (new Color (-12406529));
        actualizarUnProductoButton.setForeground (new Color (-1));
        actualizarUnProductoButton.setText ("Un Producto");
        panel4.add (actualizarUnProductoButton, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        porCategoriaButton = new JButton ();
        porCategoriaButton.setBackground (new Color (-12406529));
        porCategoriaButton.setForeground (new Color (-1));
        porCategoriaButton.setText ("Por Categoria");
        panel4.add (porCategoriaButton, new GridConstraints (2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        generalButton = new JButton ();
        generalButton.setBackground (new Color (-12406529));
        generalButton.setForeground (new Color (-1));
        generalButton.setText ("General");
        panel4.add (generalButton, new GridConstraints (3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        productosSeleccionadosButton = new JButton ();
        productosSeleccionadosButton.setBackground (new Color (-12406529));
        productosSeleccionadosButton.setForeground (new Color (-1));
        productosSeleccionadosButton.setText ("Productos Seleccionados");
        panel4.add (productosSeleccionadosButton, new GridConstraints (1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelClose = new JPanel ();
        panelClose.setLayout (new GridLayoutManager (3, 1, new Insets (5, 5, 5, 5), -1, -1));
        panelClose.setBackground (new Color (-1));
        panelProducto.add (panelClose, new GridConstraints (6, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        guardarButton = new JButton ();
        guardarButton.setBackground (new Color (-12406529));
        guardarButton.setForeground (new Color (-1));
        guardarButton.setText ("Guardar");
        panelClose.add (guardarButton, new GridConstraints (2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deshacerTodoButton = new JButton ();
        deshacerTodoButton.setBackground (new Color (-1));
        deshacerTodoButton.setEnabled (true);
        deshacerTodoButton.setForeground (new Color (-12406529));
        deshacerTodoButton.setText ("Deshacer Todo");
        panelClose.add (deshacerTodoButton, new GridConstraints (1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deshacerCambiosButton = new JButton ();
        deshacerCambiosButton.setBackground (new Color (-1249286));
        deshacerCambiosButton.setText ("Deshacer");
        panelClose.add (deshacerCambiosButton, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer ();
        panelProducto.add (spacer1, new GridConstraints (4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel5 = new JPanel ();
        panel5.setLayout (new GridLayoutManager (1, 4, new Insets (0, 0, 0, 0), -1, -1));
        panel5.setBackground (new Color (-1));
        panel5.setEnabled (true);
        panelProducto.add (panel5, new GridConstraints (7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        siguienteButton = new JButton ();
        siguienteButton.setBackground (new Color (-12406529));
        siguienteButton.setForeground (new Color (-1));
        siguienteButton.setText ("Siguiente");
        panel5.add (siguienteButton, new GridConstraints (0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        anteriorButton = new JButton ();
        anteriorButton.setBackground (new Color (-12406529));
        anteriorButton.setEnabled (false);
        anteriorButton.setForeground (new Color (-1));
        anteriorButton.setText ("Anterior");
        panel5.add (anteriorButton, new GridConstraints (0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer ();
        panel5.add (spacer2, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        pageCount = new JLabel ();
        pageCount.setForeground (new Color (-16777216));
        pageCount.setText ("0");
        panel5.add (pageCount, new GridConstraints (0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel ();
        panel6.setLayout (new GridLayoutManager (2, 1, new Insets (5, 5, 5, 5), -1, -1));
        panel6.setBackground (new Color (-1));
        panelProducto.add (panel6, new GridConstraints (3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        eliminarProductoButton = new JButton ();
        eliminarProductoButton.setBackground (new Color (-12406529));
        eliminarProductoButton.setForeground (new Color (-1));
        eliminarProductoButton.setText ("Eliminar Producto");
        panel6.add (eliminarProductoButton, new GridConstraints (1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel ();
        Font label4Font = this.$$$getFont$$$ (null, -1, 18, label4.getFont ());
        if ( label4Font != null ) label4.setFont (label4Font);
        label4.setText ("Eliminar Producto");
        panel6.add (label4, new GridConstraints (0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        return panelProducto;
    }

}
