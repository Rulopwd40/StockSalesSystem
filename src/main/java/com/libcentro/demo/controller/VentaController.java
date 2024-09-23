package com.libcentro.demo.controller;

import com.libcentro.demo.model.Producto;
import com.libcentro.demo.model.ProductoFStock;
import com.libcentro.demo.model.Venta;
import com.libcentro.demo.model.Venta_Producto;
import com.libcentro.demo.services.ProductoService;
import com.libcentro.demo.services.VentaService;
import com.libcentro.demo.utils.FieldAnalyzer;
import com.libcentro.demo.utils.filters.Filter;
import com.libcentro.demo.view.venta.ApfsDialog;
import com.libcentro.demo.view.venta.VentaFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Optional;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

@Controller
public class VentaController {

    private final VentaService ventaService;
    private final ProductoService productoService;
    Venta venta;
    VentaFrame ventaFrame;
    ViewController viewController;
    private JTable tableVenta;
    private DefaultTableModel ventaTableModel;
    ApfsDialog apfsDialog;
    private String codigo_barras;


    @Autowired
    public VentaController(@Lazy ViewController viewController, VentaService ventaService, ProductoService productoService) {
        this.viewController = viewController;
        this.ventaService = ventaService;
        this.productoService = productoService;
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
        ventaTableModel = (DefaultTableModel) tableVenta.getModel();
        //Listener de actualizacion
        ventaTableModel.addTableModelListener(new TableModelListener(){
            public void tableChanged(TableModelEvent e) {
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

            this.codigo_barras = ventaFrame.getCodBar().getText();
            var cantidad= Integer.parseInt(ventaFrame.getCant().getText());
            agregarProducto(codigo_barras,cantidad);
            ventaFrame.getCodBar().setText("");
            ventaFrame.getCant().setText("1");
            ventaFrame.setCodFocus();

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

        //Cargar datos
        apfsDialog.getButtonOK().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(FieldAnalyzer.todosLosCamposLlenos(apfsDialog)) {
                    String nombre = apfsDialog.getNombreField().getText();
                    String cantidad = apfsDialog.getCantField().getText();
                    String precio = apfsDialog.getPrecioField().getText();

                    ProductoFStock producto;
                    producto = new ProductoFStock(nombre,cantidad,precio,"0");
                    venta.addProducto(producto);


                    apfsDialog.onOK();

                    updateTableVenta();
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

    }

    private void agregarProducto(String codigo_barras, int cantidad) {
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(null, "Ingrese cantidad del producto mayor a 0");
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        Producto producto=null;
        try {
            producto = productoService.getProducto(codigo_barras, cantidad);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se puede agregar el producto: " + e.getMessage());
        }
        Producto finalProducto = producto;
        if(finalProducto != null) {
            Venta_Producto ventaProducto = venta.getListaProductos().stream()
                    .filter(ventap -> ventap.getProducto().equals(finalProducto))
                    .findFirst()
                    .orElse(null);

            if (ventaProducto != null) {
                ventaProducto.setProducto(producto, ventaProducto.getCantidad() + cantidad);
            } else {
                venta.addProducto(producto, cantidad);
            }
        }
        updateTableVenta();
    }

    private void updateTableVenta(){
        ventaTableModel.setRowCount(0);

        for(Venta_Producto ventaProducto: venta.getListaProductos()){
            Producto producto= ventaProducto.getProducto();
            ventaTableModel.addRow(new Object[] {producto.getNombre(),ventaProducto.getCantidad(),ventaProducto.getDescuento(),ventaProducto.getPrecio_venta()});
        }
        for(ProductoFStock productoFStock: venta.getListaProductosF()){
            ventaTableModel.addRow(new Object[] {productoFStock.getNombre(),productoFStock.getPrecio_venta(), productoFStock.getDescuento(), productoFStock.getCantidad() });
        }

        venta.updateTotal();
        ventaFrame.getTotalPrice().setText(venta.getTotal()+"");
    }

    void closeVentaFrame(){
        ventaFrame.setVisible(false);
        ventaFrame.dispose();
        ventaFrame = null;

    }

}
