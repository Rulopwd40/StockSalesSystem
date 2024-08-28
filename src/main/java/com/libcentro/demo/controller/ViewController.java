package com.libcentro.demo.controller;

import com.libcentro.demo.model.Producto;
import com.libcentro.demo.model.ProductoFStock;
import com.libcentro.demo.model.Venta;
import com.libcentro.demo.model.Venta_Producto;
import com.libcentro.demo.utils.PrecioFilter;
import com.libcentro.demo.view.ApfsDialog;
import com.libcentro.demo.view.MenuFrame;
import com.libcentro.demo.view.VentaFrame;
import com.libcentro.demo.utils.DoubleFilter;
import com.libcentro.demo.utils.IntegerFilter;
import com.libcentro.demo.utils.SymbolFilter;

import javax.sound.midi.SysexMessage;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.*;
import java.util.Optional;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

public class ViewController {

    MenuFrame menuFrame;
    VentaFrame ventaFrame;
    ApfsDialog apfsDialog;
    private JTable tableVenta;
    Venta venta;

    public ViewController() {
        openMenuView();

    }

    //Abre el menú, asigna restricciones y listeners
    private void openMenuView(){
        menuFrame = new MenuFrame();
        menuFrame.setVisible(true);

        // Menu Listeners
        menuFrame.getProductosButton().addActionListener(e -> openProductosView());
        menuFrame.getVentaButton().addActionListener(e -> newVenta());
        menuFrame.getReportesButton().addActionListener(e -> openEstadisticasView());

        // KeyBindings for MenuFrame
        menuFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "openVenta");

        menuFrame.getRootPane().getActionMap().put("openVenta", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newVenta();
            }
        });
    }

    private void openProductosView() {
        System.out.println("open productos view");
    }


    //Crear Venta
    private void newVenta(){

        openVentaView();
        venta = new Venta();


    }

    //Abre la ventana view, asigna restricciones y listeners
    private void openVentaView() {

        if(ventaFrame == null) {
            ventaFrame = new VentaFrame();
            ventaFrame.setVisible(true);
            tableVenta=ventaFrame.getTable();
            // Venta Listeners
           setVentaListeners();


            //Restricciones de inputs
            setIntegerFilter(ventaFrame.getCodBar());
            setIntegerFilter(ventaFrame.getCant());

            // KeyBindings for VentaFrame
            setVentaKeyBindings();

        }
        //Listener de actualizacion
        tableVenta.getModel().addTableModelListener(new TableModelListener(){
            public void tableChanged(TableModelEvent e){
                if (e.getType() == TableModelEvent.UPDATE) {
                    int fila = e.getFirstRow();
                    int columna = e.getColumn();

                    // Obtén el valor actualizado
                    Object valorNuevo = tableVenta.getModel().getValueAt(fila, columna);
                    String nombreColumna = tableVenta.getModel().getColumnName(columna);
                    String nombreProducto = tableVenta.getModel().getValueAt(fila,0).toString();

                    System.out.println(nombreProducto);
                    System.out.println(nombreColumna);

                    updateProducto(nombreProducto,nombreColumna,fila,columna);
                }
            }

        });
        ventaFrame.setState(Frame.NORMAL); // Restaurar si está minimizado
        ventaFrame.toFront();
        ventaFrame.requestFocus();

    }

    private void openEstadisticasView() {
        System.out.println("open estadisticas view");
    }


    //Abre el dialogo para agregar un producto fuera de stock
    private void openApfsDialog() {
        apfsDialog = new ApfsDialog();

        setSymbolFilter(apfsDialog.getNombreField());
        setIntegerFilter(apfsDialog.getCantField());
        setPrecioFilter(apfsDialog.getPrecioField());


        //Cargar datos
        apfsDialog.getButtonOK().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(apfsDialog.todosLosCamposLlenos()) {
                    //Obtener datos
                    String nombre = apfsDialog.getNombreField().getText();
                    String cantidad = apfsDialog.getCantField().getText();
                    String precio = apfsDialog.getPrecioField().getText();

                   //Crear Tabla
                    DefaultTableModel model = (DefaultTableModel) ventaFrame.getTable().getModel();

                    //Crear Producto
                    ProductoFStock producto = new ProductoFStock();
                    producto.setNombre(nombre);
                    producto.setCantidad(Integer.parseInt(cantidad));
                    producto.setPrecio_venta(Float.parseFloat(precio));

                    //Agregar producto a view y venta
                    model.addRow(new Object[]{producto.getNombre(), producto.getCantidad(), 0f,producto.getPrecio_venta()});
                    venta.addProducto(producto);

                    ventaFrame.getTotalPrice().setText(venta.getTotal() + "");

                    apfsDialog.onOK();
                }
            }
        });

        apfsDialog.getButtonCancel().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apfsDialog.onCancel();
            }
        });

        // call onCancel() when cross is clicked
        apfsDialog.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        apfsDialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                apfsDialog.onCancel();
            }
        });

        // call onCancel() on ESCAPE
        apfsDialog.getApfsPane().registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                apfsDialog.onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        apfsDialog.setVisible(true);
    }

    private void closeVentaFrame(){
        ventaFrame.setVisible(false);
        ventaFrame.dispose();
        ventaFrame = null;

    }


    //Venta
    private void setVentaKeyBindings(){
        ventaFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "focusCodBar");

        ventaFrame.getRootPane().getActionMap().put("focusCodBar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ventaFrame.getCodBar().requestFocusInWindow();
            }
        });

        ventaFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "closeVenta");

        ventaFrame.getRootPane().getActionMap().put("closeVenta", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeVentaFrame();
            }
        });


        //Open ApfsDialog
        ventaFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "openApfsDialog");

        ventaFrame.getRootPane().getActionMap().put("openApfsDialog", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openApfsDialog();
            }
        });

        //CodBar Focus
        ventaFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "setCodFocus");
        ventaFrame.getRootPane().getActionMap().put("setCodFocus", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventaFrame.setCodFocus();
            }
        });
    }

    private void setVentaListeners(){
        ventaFrame.getAgregarEnterButton().addActionListener(e -> {
            ventaFrame.setCodFocus();
            ventaFrame.getCodBar().getText();

        });
        ventaFrame.getAgregarProductoFueraDeButton().addActionListener(e -> openApfsDialog());
        ventaFrame.getCancelarEscButton().addActionListener(e -> closeVentaFrame());
        ventaFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeVentaFrame();
            }
        });
    }

    //Producto
    private void updateProducto(String nombre, String atributo,int fila,int columna) {
        // Buscar el producto en ambas listas y manejar la lógica de encontrar un producto
        Optional<Producto> productoOpt = venta.getListaProductos().stream()
                .map(Venta_Producto::getProducto)
                .filter(p -> p.getNombre().equals(nombre))
                .findFirst();

        Optional<ProductoFStock> productoFStockOpt;

        if (productoOpt.isEmpty()) {
            productoFStockOpt = venta.getListaProductosF().stream()
                    .filter(p -> p.getNombre().equals(nombre))
                    .findFirst();
        } else {
            productoFStockOpt = Optional.empty();
        }

        // Manejar los resultados de las búsquedas
        productoOpt.ifPresentOrElse(
                producto -> {
                    if(atributo.equals("Cantidad")) {
                    }
                    else if(atributo.equals("Descuento(%)")){
                    }
                    else System.out.println(atributo);
                },
                () -> productoFStockOpt.ifPresentOrElse(
                        productoFStock -> {
                            if(atributo.equals("Cantidad")) {
                                productoFStock.setCantidad((Integer) tableVenta.getValueAt(fila,columna));
                            }
                            else if(atributo.equals("Descuento(%)")){
                                productoFStock.setDescuento((Float) tableVenta.getValueAt(fila,columna));
                            }
                            else System.out.println(atributo);
                        },
                        () -> System.out.println("Producto no encontrado")
                )
        );
        venta.updateTotal();
        ventaFrame.getTotalPrice().setText(venta.getTotal() + "");
    }


    //Filters
    private void setIntegerFilter(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new IntegerFilter());
    }

    private void setDoubleFilter(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DoubleFilter());
    }

    private void setSymbolFilter(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new SymbolFilter());
    }
    private void setPrecioFilter(JTextField textField){
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new PrecioFilter());
    }
}