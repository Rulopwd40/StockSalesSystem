package com.libcentro.demo.controller;

import java.awt.*;
import java.util.List;

import com.libcentro.demo.view.ProductosFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.libcentro.demo.model.Producto;

import com.libcentro.demo.services.interfaces.IproductoService;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

@Controller
public class ProductosController {

    private final IproductoService productoService;
    List<Producto> productos;
    ViewController viewController;
    ProductosFrame productosFrame;

    @Autowired
    public ProductosController(@Lazy ViewController viewController, IproductoService productoService) {
        this.viewController = viewController;
        this.productoService = productoService;
    }

    public void openProductosFrame(){
        if(productosFrame == null){
            productosFrame = new ProductosFrame();

        }

        productosFrameAddListeners();
        productosFrameUpdateTable();

        productosFrame.setVisible(true);
        productosFrame.setState(Frame.NORMAL); // Restaurar si está minimizado
        productosFrame.toFront();
        productosFrame.requestFocus();
    }

    private void productosFrameAddListeners(){
        productosFrame.getBuscarField().getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    public void productosFrameUpdateTable() {
        // Obtener los productos de la base de datos
        productos = getAll();

        // Obtener el modelo de la tabla
        DefaultTableModel model = (DefaultTableModel) productosFrame.getTable().getModel();

        // Limpiar todas las filas actuales del modelo de la tabla
        model.setRowCount(0);

        // Agregar los productos al modelo de la tabla
        for (Producto producto : productos) {
            model.addRow(new Object[]{
                    producto.getCodigo_barras(),
                    producto.getNombre(),
                    producto.getCategoria(),
                    producto.getStock(),
                    producto.getCosto_compra(),
                    producto.getPrecio_venta()
            });
        }
    }



    public List<Producto> getAll() {
        return productoService.getAll();
    }

    public void cargarMasiva(List<Producto> testInventario) {
        for (Producto d : testInventario) {
            productoService.saveProducto(d);
        }
    }

    public void listarProductos() {
        for (Producto d : productoService.getAll()) {
            System.out.println(d.toString());
        }
    }
    public void saveProducto(Producto x) {
        productoService.saveProducto(x);
    }


}
