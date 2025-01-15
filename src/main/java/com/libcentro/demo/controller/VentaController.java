package com.libcentro.demo.controller;

import com.libcentro.demo.exceptions.EmptyFieldException;
import com.libcentro.demo.exceptions.InsufficientStockException;
import com.libcentro.demo.model.dto.ProductoDTO;
import com.libcentro.demo.model.dto.ProductoFStockDTO;
import com.libcentro.demo.model.dto.VentaDTO;
import com.libcentro.demo.model.dto.Venta_ProductoDTO;
import com.libcentro.demo.services.ProductoService;
import com.libcentro.demo.services.VentaService;
import com.libcentro.demo.services.interfaces.IproductoService;
import com.libcentro.demo.services.interfaces.IventaService;
import com.libcentro.demo.utils.FieldAnalyzer;
import com.libcentro.demo.utils.filters.Filter;
import com.libcentro.demo.view.venta.ApfsDialog;
import com.libcentro.demo.view.venta.VentaFrame;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

@Controller
public class VentaController {

    @Autowired
    private final IventaService ventaService;
    @Autowired
    private final IproductoService productoService;

    private final StockController stockController;

    VentaDTO venta;
    VentaFrame ventaFrame;
    ViewController viewController;
    private JTable tableVenta;
    private DefaultTableModel ventaTableModel;
    ApfsDialog apfsDialog;


    @Autowired
    public VentaController(@Lazy ViewController viewController, VentaService ventaService, ProductoService productoService, StockController stockController) {
        this.viewController = viewController;
        this.ventaService = ventaService;
        this.productoService = productoService;
        this.stockController = stockController;
    }

    void openVentaFrame() {
        venta = new VentaDTO ();
        if(ventaFrame == null) {
            ventaFrame = new VentaFrame();
            ventaFrame.setVisible(true);
            tableVenta=ventaFrame.getTable();
            // Venta Listeners
            setVentaListeners();


            //Restricciones de inputs
            Filter.setIntegerFilter(ventaFrame.getCodBar());
            Filter.setIntegerFilter(ventaFrame.getCant());
            Filter.setPrecioFilter(ventaFrame.getDescuentoField());

            // KeyBindings for VentaFrame
            setVentaKeyBindings();

        }
        ventaTableModel = (DefaultTableModel) tableVenta.getModel();



        //Listener de actualizacion
        ventaTableModel.addTableModelListener(new TableModelListener(){
            public void tableChanged(TableModelEvent e) {
                if(e.getType() == TableModelEvent.UPDATE) {
                    int fila = e.getFirstRow();
                    int columna = e.getColumn();

                    String nombre = tableVenta.getValueAt(fila, 0) +"";
                    String valor = tableVenta.getValueAt(fila, columna)+"";
                    Object producto = getProductoFromVenta(nombre,fila);


                    updateProducto(producto,valor,columna);
                    updateTableVenta();
                }
            }
        });

        ventaFrame.setState(Frame.NORMAL); // Restaurar si está minimizado
        ventaFrame.toFront();
        ventaFrame.requestFocus();
        ventaFrame.setCodFocus();

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


        // KeyBinding para eliminar fila seleccionada al presionar "Suprimir"
        tableVenta.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "deleteRow");
        tableVenta.getActionMap().put("deleteRow", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto ();
            }
        });


        //CodBar Focus
        ventaFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "agregarProducto");
        ventaFrame.getRootPane().getActionMap().put("agregarProducto", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ventaFrame.getCodBar().isFocusOwner() && !ventaFrame.getCodBar().getText().isEmpty()){
                    ventaFrame.getCant().setText("");
                    ventaFrame.setCantFocus();
                }
                else{
                    agregarProducto();
                    ventaFrame.setCodFocus();
                }

            }
        });

        //Vender
        ventaFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_F3,0),"vender");
        ventaFrame.getRootPane().getActionMap().put("vender", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vender();
            }
        });
    }
    private void setVentaListeners(){
        // Action Listener para el botón
        ventaFrame.getAgregarEnterButton().addActionListener(e -> {
            agregarProducto();
        });
        ventaFrame.getAgregarProductoFueraDeButton().addActionListener(e -> openApfsDialog());
        ventaFrame.getCancelarEscButton().addActionListener(e -> closeVentaFrame());
        ventaFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeVentaFrame();
            }
        });

        ventaFrame.getDescuentoButton().addActionListener(e -> {

            try {
                FieldAnalyzer.campoLleno(ventaFrame.getDescuentoField());
            }catch(EmptyFieldException ex){
                JOptionPane.showMessageDialog(ventaFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                throw new EmptyFieldException("Llene el campo");
            }
            float descuento= Float.parseFloat(ventaFrame.getDescuentoField().getText());
            if(descuento >100d || descuento <0){
                ventaFrame.getDescuentoField().setText("");
                JOptionPane.showMessageDialog(ventaFrame, "Ingrese un descuento entre [0;100]", "Error", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Ingrese un descuento entre [0;100]");
            }
            for(Object producto: venta.getTodosLosProductos()) {
                if(producto instanceof Venta_ProductoDTO p) {
                   p.setDescuento(descuento);
                }
                if(producto instanceof ProductoFStockDTO p) {
                    p.setDescuento(descuento);
                }
            }
            updateTableVenta();
        });

        ventaFrame.getGenerarTicketF3Button().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vender();
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

                    ProductoFStockDTO producto;
                    producto = new ProductoFStockDTO(nombre,cantidad,precio,"0");
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
    //Update
    private void updateProducto(Object producto,String valor,int columna){
        if(producto instanceof Venta_ProductoDTO){
            updateProducto((Venta_ProductoDTO) producto,valor,columna);
        }
        else if(producto instanceof ProductoFStockDTO){
            updateProducto((ProductoFStockDTO) producto,valor,columna);
        }
        else{
            throw new RuntimeException("La clase del producto no adhiere a la las clases predeterminadas");
        }
    }
    private void updateProducto(Venta_ProductoDTO ventaProducto,String valor,int columna) {
            switch (columna){
                case 1:
                    ventaProducto.setCantidad(Integer.parseInt(valor));
                    break;
                case 2:
                    ventaProducto.setDescuento(Float.parseFloat(valor));
                    break;
                default:
                    throw new RuntimeException("No se puede editar esta columna");
            }
    }
    private void updateProducto(ProductoFStockDTO ventaProducto,String valor,int columna) {
        switch (columna){
            case 1:
                ventaProducto.setCantidad(Integer.parseInt(valor));
                break;
            case 2:
                ventaProducto.setDescuento(Float.parseFloat(valor));
                break;
            default:
                throw new RuntimeException("No se puede editar esta columna");
        }
    }

    //Agregar
    private void agregarProducto(){
        try{
            FieldAnalyzer.todosLosCamposLlenos(ventaFrame.getAgregarProductoFieldPanel());
        }catch(EmptyFieldException ex){
            JOptionPane.showMessageDialog(ventaFrame, "Complete todos los campos");
            throw new EmptyFieldException("Completar todos los campos");
        }
        String codigo_barras = ventaFrame.getCodBar().getText ();
        var cantidad= Integer.parseInt(ventaFrame.getCant().getText());
        agregarProducto(codigo_barras,cantidad);
        ventaFrame.getCodBar().setText("");
        ventaFrame.getCant().setText("");
        ventaFrame.setCodFocus();

    }
    private void agregarProducto(String codigo_barras, int cantidad) {
        ProductoDTO producto;
        try {
            producto = productoService.getProductoDTO (codigo_barras, cantidad);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, "No se puede agregar el producto: " + e.getMessage());
            throw new RuntimeException (e.getMessage ());
        }

        Venta_ProductoDTO ventaProducto = venta.getVenta_producto().stream()
                    .filter(ventap -> ventap.getProducto().equals(producto))
                    .findFirst()
                    .orElse(null);

        if (ventaProducto != null) {
            if (producto.getStock() < ventaProducto.getCantidad() + cantidad) {
                JOptionPane.showMessageDialog(null, "El producto " + producto.getNombre() + " con código: " + producto.getCodigobarras () + " no tiene stock suficiente.");
                throw new InsufficientStockException("El producto " + producto.getNombre() + " con código: " + producto.getCodigobarras () + " no tiene stock suficiente.");
            }
            ventaProducto.setProducto(producto, ventaProducto.getCantidad() + cantidad);
        } else {
            venta.addProducto(producto, cantidad);
        }

        updateTableVenta();
    }

    //Eliminar
    private void eliminarProducto() {
        int selectedRow = tableVenta.getSelectedRow();
        if (selectedRow != -1) {
            String productoNombre = (String) tableVenta.getValueAt(selectedRow, 0);
            Object producto = getProductoFromVenta(productoNombre, selectedRow);

            if (producto instanceof Venta_ProductoDTO) {
                venta.getVenta_producto().remove(producto);
            } else if (producto instanceof ProductoFStockDTO) {
                venta.getProducto_fstock().remove(producto);
            }

            updateTableVenta();
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un producto para eliminar.");
        }
    }

    private void updateTableVenta(){
        ventaTableModel.setRowCount(0);

        for(Venta_ProductoDTO ventaProducto: venta.getVenta_producto()){
            if(ventaProducto.getCantidad () == 0) venta.getVenta_producto ().remove (ventaProducto);
            ProductoDTO producto= ventaProducto.getProducto();
            ventaTableModel.addRow(new Object[] {producto.getNombre(),ventaProducto.getCantidad(),ventaProducto.getDescuento(),ventaProducto.getPrecio_venta()});
        }
        for(ProductoFStockDTO productoFStock: venta.getProducto_fstock ()){
            if(productoFStock.getCantidad () == 0) venta.getProducto_fstock ().remove (productoFStock);
            ventaTableModel.addRow(new Object[] {productoFStock.getNombre(),productoFStock.getCantidad(), productoFStock.getDescuento(), productoFStock.getPrecio_venta() });
        }

        venta.updateTotal();
        ventaFrame.getTotalPrice().setText(venta.getTotal()+"");
    }

    void closeVentaFrame(){
        ventaFrame.setVisible(false);
        ventaFrame.dispose();
        ventaFrame = null;

    }

    private Object getProductoFromVenta(String nombre,int fila) {
        Object producto=null;
        if(fila < venta.getVenta_producto().size()) {
            producto = venta.getVenta_producto().stream()
                    .filter(ventaProducto -> ventaProducto.getProducto().getNombre().equals(nombre))
                    .findFirst()
                    .orElse(null);

        }
        else {
            producto= venta.getProducto_fstock ().stream()
                    .filter(productoF -> productoF.getNombre().equals(nombre))
                    .findFirst()
                    .orElse(null);
        }

        return producto;
    }

    private void vender() {
        try{
            ventaService.vender(venta);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error en la venta: " + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace ();
            throw new RuntimeException(e.getMessage());
        }

        JOptionPane.showMessageDialog(ventaFrame,"Venta realizada con éxito","Éxito",JOptionPane.INFORMATION_MESSAGE);
        try {
            stockController.stockControl (false);
        }catch(Exception ignore){
        }
        ventaFrame.dispose();
        ventaFrame =null;
    }

}
