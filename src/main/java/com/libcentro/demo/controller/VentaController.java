package com.libcentro.demo.controller;

import com.libcentro.demo.model.Producto;
import com.libcentro.demo.model.ProductoFStock;
import com.libcentro.demo.model.Venta;
import com.libcentro.demo.model.Venta_Producto;
import com.libcentro.demo.utils.Filter;
import com.libcentro.demo.view.ApfsDialog;
import com.libcentro.demo.view.VentaFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Optional;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

@Controller
public class VentaController {
    @Autowired
    Venta venta;
    VentaFrame ventaFrame;
    ViewController viewController;
    private JTable tableVenta;
    ApfsDialog apfsDialog;

    public VentaController(ViewController viewController) {
        this.viewController = viewController;
    }

    void openVentaFrame() {
        venta = new Venta();
        if(ventaFrame == null) {
            ventaFrame = new VentaFrame();
            ventaFrame.setVisible(true);
            tableVenta=ventaFrame.getTable();
            // Venta Listeners
            setVentaListeners();


            //Restricciones de inputs
            Filter.setIntegerFilter(ventaFrame.getCodBar());
            Filter.setIntegerFilter(ventaFrame.getCant());

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


                    updateProducto(nombreProducto,nombreColumna,fila,columna);
                }
                ventaFrame.getTotalPrice().setText(venta.getTotal() + "");
            }

        });

        ventaFrame.setState(Frame.NORMAL); // Restaurar si está minimizado
        ventaFrame.toFront();
        ventaFrame.requestFocus();

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

    private void openApfsDialog() {
        apfsDialog = new ApfsDialog();

        Filter.setSymbolFilter(apfsDialog.getNombreField());
        Filter.setIntegerFilter(apfsDialog.getCantField());
        Filter.setPrecioFilter(apfsDialog.getPrecioField());


        //Cargar datos
        apfsDialog.getButtonOK().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(apfsDialog.todosLosCamposLlenos()) {
                    String nombre = apfsDialog.getNombreField().getText();
                    String cantidad = apfsDialog.getCantField().getText();
                    String precio = apfsDialog.getPrecioField().getText();

                    DefaultTableModel model = (DefaultTableModel) ventaFrame.getTable().getModel();
                    ProductoFStock producto;
                    producto = new ProductoFStock(nombre,cantidad,precio,"0");
                    venta.addProducto(producto);
                    model.addRow(new Object[]{nombre,Integer.parseInt(cantidad), 0.0f,Float.parseFloat(precio)});



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
                        // Lógica para "Cantidad"
                    }
                    else if(atributo.equals("Descuento(%)")){
                        // Lógica para "Descuento(%)"
                    }
                    else {
                        System.out.println(atributo);
                    }
                },
                () -> productoFStockOpt.ifPresentOrElse(
                        productoFStock -> {
                            switch(columna){
                                case 1:
                                    productoFStock.setCantidad((Integer) tableVenta.getValueAt(fila, columna));
                                    break;
                                case 2:
                                    productoFStock.setDescuento((Float) tableVenta.getValueAt(fila, columna));
                                    break;
                                case 3:
                                    productoFStock.setPrecio_venta((Float) tableVenta.getValueAt(fila, columna));
                                    break;
                                default:
                                    System.out.println(atributo);
                                    break;
                            }
                        },
                        () -> System.out.println("Producto no encontrado")
                )
        );
        venta.updateTotal();

    }

    void closeVentaFrame(){
        ventaFrame.setVisible(false);
        ventaFrame.dispose();
        ventaFrame = null;

    }

}
