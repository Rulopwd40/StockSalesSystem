package com.libcentro.demo.controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.libcentro.demo.model.Categoria;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.services.interfaces.IcategoriaService;
import com.libcentro.demo.utils.FieldAnalyzer;
import com.libcentro.demo.view.productos.ActualizarUnProducto;
import com.libcentro.demo.view.productos.AgregarCategoria;
import com.libcentro.demo.view.productos.AgregarProducto;
import com.libcentro.demo.view.productos.ProductosFrame;
import jakarta.persistence.JoinColumn;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.libcentro.demo.model.Producto;

import com.libcentro.demo.services.interfaces.IproductoService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


@Controller
public class ProductosController {

    private final IproductoService productoService;
    private final IcategoriaService icategoriaService;

    //Productos
    List<Producto> productos;
    List<Producto> nuevosProductos;
    List<Producto> productosEliminados;
    List<Producto> productosActualizados;

    List<Categoria> categorias;

    private boolean cambios;



    ViewController viewController;
    ProductosFrame productosFrame;

    DefaultTableModel productsModel;
    DefaultTableModel categoriasModel;

    AgregarProducto agregarProducto;
    ActualizarUnProducto actualizarUnProducto;
    AgregarCategoria agregarCategoria;


    @Autowired
    public ProductosController(@Lazy ViewController viewController, IproductoService productoService, IcategoriaService icategoriaService) {
        this.viewController = viewController;
        this.productoService = productoService;
        this.icategoriaService = icategoriaService;
    }

    public void openProductosFrame() {
        if (productosFrame == null) {
            productosFrame = new ProductosFrame();

        }
        cambios = false;
        refreshProductos();

        nuevosProductos = new ArrayList<Producto>();

        this.productsModel = (DefaultTableModel) productosFrame.getTable().getModel();
        productosFrameUpdateTable(productosFrame.getBuscarField().getText());
        categorias= getAllCategoria();

        productosFrameAddListeners();
        productosFrame.setVisible(true);
        productosFrame.setState(Frame.NORMAL); // Restaurar si está minimizado
        productosFrame.toFront();
        productosFrame.requestFocus();
    }

    private void productosFrameAddListeners(){
        productosFrame.getBuscarField().getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                productosFrameUpdateTable(productosFrame.getBuscarField().getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                productosFrameUpdateTable(productosFrame.getBuscarField().getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                productosFrameUpdateTable(productosFrame.getBuscarField().getText());
            }
        });
        productosFrame.getUnProductoButton().addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               agregarProducto();
           }
        });
        productosFrame.getAgregarCategoriaButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarCategoria();
            }
        });
        productosFrame.getActualizarUnProductoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarUnProducto();
            }
        });


        productosFrame.getGuardarYVolverButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarYVolver();
            }
        });

    }

    private void agregarProducto() {
        categorias = getAllCategoria();
        agregarProducto = new AgregarProducto();

        System.out.println(categorias);
        for(Categoria categoria : categorias) {
            agregarProducto.getCategoriaBox().addItem(categoria.getNombre());

        }
        agregarProducto.getButtonOK().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(FieldAnalyzer.todosLosCamposLlenos(agregarProducto)){

                    Categoria categoriaP = categorias.stream()
                            .filter(categoria -> categoria.getNombre().equals(agregarProducto.getCategoriaBox().getSelectedItem().toString()))
                            .findFirst().orElse(null);
                    Producto producto = new Producto(
                            agregarProducto.getCodigoField().getText(),
                            agregarProducto.getNombreField().getText(),
                            categoriaP,
                            Float.parseFloat(agregarProducto.getCostoField().getText()),
                            Float.parseFloat(agregarProducto.getPrecioField().getText()),
                            Integer.parseInt(agregarProducto.getCantidadField().getText())
                    );

                    if(existeProducto(producto)) {
                        addProductoToTable(producto);
                        nuevosProductos.add(producto);
                        cambiosHechos();
                        agregarProducto.onOK();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "El producto ya existe en la tabla", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });


        agregarProducto.setVisible(true);

    }

    private void actualizarUnProducto(){
        categorias= getAllCategoria();
        actualizarUnProducto = new ActualizarUnProducto();
        final Producto[] producto = new Producto[1];

        for(Categoria categoria : categorias) {
            actualizarUnProducto.getCategoriaBox().addItem(categoria.getNombre());
        }

        //Buscar producto
        actualizarUnProducto.getBuscarButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String codigo_barras = actualizarUnProducto.getCodigoField().getText();
                if(codigo_barras.equals("")){
                    JOptionPane.showMessageDialog(null, "Ingrese el codigo de barra del producto");
                    throw new IllegalArgumentException("Ingrese el codigo de barra del producto");
                }
                try{
                       producto[0] = productoService.getProducto(codigo_barras);
                }catch (ObjectNotFoundException e1){
                        JOptionPane.showMessageDialog(null, "El producto con codigo: " + codigo_barras + " no existe");
                    }
                actualizarUnProducto.getNombreField().setText(producto[0].getNombre());
                actualizarUnProducto.getCategoriaBox().setSelectedItem(producto[0].getCategoria() == null? "null" : producto[0].getCategoria().getNombre());
                actualizarUnProducto.getPrecioVentaField().setText(producto[0].getPrecio_venta() + "");
                actualizarUnProducto.getCostoCompraField().setText(producto[0].getCosto_compra() + "");
                actualizarUnProducto.getStockField().setText(producto[0].getStock() + "");
            }
        });
        //Actualizar producto
        actualizarUnProducto.getActualizarButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                HistorialPrecio historialPrecio = null;
                Categoria categoria= categorias.stream().filter(categoria1 -> categoria1.equals(actualizarUnProducto.getCategoriaBox().getSelectedItem()
                        .toString()))
                        .findFirst()
                        .orElse(null);
                int cantidadAgregada = Integer.parseInt(actualizarUnProducto.getStockField().getText()) - producto[0].getStock();
                float costo_compra = Float.parseFloat(actualizarUnProducto.getCostoCompraField().getText());
                if(cantidadAgregada>0 || producto[0].getCosto_compra() != costo_compra) {
                   historialPrecio = new HistorialPrecio(producto[0], costo_compra, cantidadAgregada);
                    producto[0].getHistorial_precios().add(historialPrecio);
                }
                producto[0].setNombre(actualizarUnProducto.getNombreField().getText());
                producto[0].setCategoria(categoria);
                producto[0].setCosto_compra(costo_compra);
                producto[0].setPrecio_venta(Float.parseFloat(actualizarUnProducto.getPrecioVentaField().getText()));
                producto[0].setStock(cantidadAgregada);
                try {
                    productoService.updateProducto(producto[0]);
                }catch(RuntimeException re){
                    JOptionPane.showMessageDialog(null, "Error al actualizar el producto " + re.getMessage());
                }
            }
        });
        //Cerrar
        actualizarUnProducto.getCerrarButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                productosFrameUpdateTable("");
            }
        });


        actualizarUnProducto.setVisible(true);
    }


    //Si bien dice agregar categoria aquí tambien se maneja la eliminación
    private void agregarCategoria() {
        categorias = getAllCategoria();
        agregarCategoria = new AgregarCategoria();

        categoriasModel = (DefaultTableModel) agregarCategoria.getTablaCategorias().getModel();
        ListSelectionModel categoriasSelectionModel = (ListSelectionModel) agregarCategoria.getTablaCategorias().getSelectionModel();

        for(Categoria categoria : categorias) {
            categoriasModel.addRow(new Object[]{categoria.getNombre()});
        }

        categoriasSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                agregarCategoria.getEliminarButton().setEnabled(true);
            }
        });
        agregarCategoria.getEliminarButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                var filaSeleccionada = agregarCategoria.getTablaCategorias().getSelectedRow();
                var columnaSeleccionada = agregarCategoria.getTablaCategorias().getSelectedColumn();
                Categoria categoria = categorias.stream()
                        .filter(categoriaT -> categoriaT.getNombre() == categoriasModel.getValueAt(filaSeleccionada, columnaSeleccionada))
                        .findFirst().orElse(null);

                try{
                    icategoriaService.deleteCategoria(categoria);
                } catch (Exception exception){
                    System.out.println(exception.getMessage());
                }

                JOptionPane.showMessageDialog(null,"Categoria " + categoria.getNombre() + " eliminada exitosamente", "Eliminacion exitosa",JOptionPane.INFORMATION_MESSAGE);
                categoriasModel.removeRow(filaSeleccionada);

                agregarCategoria.getEliminarButton().setEnabled(false);


            }
        });
        agregarCategoria.getCategoriaField().getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                verificarCampo();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                verificarCampo();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
            private void verificarCampo(){
                if(agregarCategoria.getCategoriaField().getText().isEmpty()) {
                    agregarCategoria.getAgregarButton().setEnabled(false);
                }
                else {
                    agregarCategoria.getAgregarButton().setEnabled(true);
                }
            }
        });
        agregarCategoria.getAgregarButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Categoria categoria = new Categoria(agregarCategoria.getCategoriaField().getText());
                try{
                    icategoriaService.saveCategoria(categoria);
                } catch (Exception exception){
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(null,"Categoría agregada exitosamente","Éxito",JOptionPane.INFORMATION_MESSAGE);
                categoriasModel.addRow(new Object[] {agregarCategoria.getCategoriaField().getText()});
            }
        });
        agregarCategoria.getButtonOK().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productosFrameUpdateTable("");
            }
        });

        agregarCategoria.setVisible(true);


    }



    private void productosFrameUpdateTable(String filter) {

        // Obtener el modelo de la tabla


        // Limpiar todas las filas actuales del modelo de la tabla
        productsModel.setRowCount(0);

        List<Producto> todosProductos = Stream.concat(productos.stream(), nuevosProductos.stream())
                .toList();

        String filterT = filter.toLowerCase();

        List<Producto> productosFiltrados = todosProductos.stream()
                .filter(producto -> producto.getNombre().toLowerCase().matches(Pattern.quote(filterT) + ".*") ||
                        producto.getCodigo_barras().toLowerCase().matches(Pattern.quote(filterT) + ".*") ||
                        producto.getCategoria().getNombre().toLowerCase().matches(Pattern.quote(filterT) + ".*")
                )
                .toList();

        // Agregar los productos al modelo de la tabla
        for (Producto producto : productosFiltrados) {
            addProductoToTable(producto);
        }
    }

    private void refreshProductos(){
        productos = getAllProducto();
    }

    private List<Producto> getAllProducto() {
        return productoService.getAll();
    }

    private List<Categoria> getAllCategoria(){
        return icategoriaService.getAll();
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

    private void addProductoToTable(Producto producto){
        Categoria categoria = producto.getCategoria();


        productsModel.addRow(new Object[]{
                producto.getCodigo_barras(),
                producto.getNombre(),
                categoria == null? "null" : categoria.getNombre(),
                producto.getStock(),
                producto.getCosto_compra(),
                producto.getPrecio_venta()
        });
    }

    private boolean existeProducto(Producto producto){

        Producto p = productos.stream().filter(prod -> prod.getCodigo_barras().equals(producto.getCodigo_barras())).findFirst().orElse(null);
        Producto pn = nuevosProductos.stream().filter(prod -> prod.getCodigo_barras().equals(producto.getCodigo_barras())).findFirst().orElse(null);
        return p == null && pn == null;
    }

    private void cambiosHechos(){
        cambios = true;
        productosFrame.getGuardarYVolverButton().setEnabled(true);
    }

    protected void guardarYVolver() {
        if (cambios) {
            for (Producto producto : nuevosProductos) {
                try {
                    productoService.crearProducto(producto);
                } catch (Exception e) {
                    // Manejar cualquier excepción de manera genérica
                    JOptionPane.showMessageDialog(null, "Error al guardar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            cambios = false;
            productosFrame.getGuardarYVolverButton().setEnabled(false);
        }
    }

}
