package com.libcentro.demo.controller;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.libcentro.demo.exceptions.EmptyFieldException;
import com.libcentro.demo.exceptions.OutOfBounds;
import com.libcentro.demo.exceptions.ProductExistsInDataBase;
import com.libcentro.demo.exceptions.ProductNameExists;
import com.libcentro.demo.model.Categoria;
import com.libcentro.demo.services.CategoriaService;
import com.libcentro.demo.services.interfaces.IcategoriaService;
import com.libcentro.demo.utils.FieldAnalyzer;
import com.libcentro.demo.utils.command.CommandInvoker;
import com.libcentro.demo.utils.filters.Filter;
import com.libcentro.demo.view.productos.*;
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

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;


@Controller
public class ProductosController {

    private final IproductoService productoService;
    private final IcategoriaService categoriaService;


    //Productos
    List<Producto> productos;

    List<Categoria> categorias;

    ViewController viewController;
    ProductosFrame productosFrame;

    DefaultTableModel productsModel;
    DefaultTableModel categoriasModel;

    AgregarProducto agregarProducto;
    ActualizarUnProducto actualizarUnProducto;
    ActualizarPorCategoria actualizarPorCategoria;

    AgregarCategoria agregarCategoria;


    @Autowired
    public ProductosController(@Lazy ViewController viewController, IproductoService productoService, IcategoriaService categoriaService) {
        this.viewController = viewController;
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    public void openProductosFrame() {
        if (productosFrame == null) {
            productosFrame = new ProductosFrame();

        }

        refreshProductos();

        this.productsModel = (DefaultTableModel) productosFrame.getTable().getModel();
        productosFrameUpdateTable(productosFrame.getBuscarField().getText());
        categorias= getAllCategoria();

        // Añadir un mapeo para la tecla F5
        productosFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "refreshTable");

        // Asignar una acción a la tecla F5
        productosFrame.getRootPane().getActionMap().put("refreshTable", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshProductos();
                productosFrameUpdateTable();
            }
        });

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
        productosFrame.getPorCategoriaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProductoPorCategoria();
            }
        });

        productosFrame.getDeshacerCambiosButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deshacer();
            }
        });
        productosFrame.getDeshacerTodoButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deshacerTodo();
            }
        });

        productosFrame.getActualizarTablaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productosFrameUpdateTable();
            }
        });

        productosFrame.getGeneralButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizacionGeneral();
            }
        });
        productosFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                save();
            }
        });
        productosFrame.getGuardarButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                save();
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
                try{
                    FieldAnalyzer.todosLosCamposLlenos(agregarProducto);
                    Categoria categoriaP = categorias.stream()
                            .filter(categoria -> categoria.getNombre().equals(agregarProducto.getCategoriaBox().getSelectedItem().toString()))
                            .findFirst().orElseThrow();
                    Producto producto = new Producto(
                            agregarProducto.getCodigoField().getText(),
                            agregarProducto.getNombreField().getText(),
                            categoriaP,
                            Float.parseFloat(agregarProducto.getCostoField().getText()),
                            Float.parseFloat(agregarProducto.getPrecioField().getText()),
                            Integer.parseInt(agregarProducto.getCantidadField().getText())
                    );

                        existeProducto(producto);
                        productoService.crearProducto(producto);
                        agregarProducto.onOK();
                    }catch(RuntimeException ex) {
                        JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException(ex);
                    }
                    productosFrameUpdateTable();
                }
        });


        agregarProducto.setVisible(true);

    }

    //Actualizacion
    private void actualizarUnProducto(){
        categorias= getAllCategoria();
        actualizarUnProducto = new ActualizarUnProducto();

        final Producto[] producto = new Producto[1];

        for(Categoria categoria : categorias) {
            actualizarUnProducto.getCategoriaBox().addItem(categoria.getNombre());
        }

        Filter.setIntegerFilter(actualizarUnProducto.getCodigoField());
        Filter.setIntegerFilter(actualizarUnProducto.getStockField());
        Filter.setPrecioFilter(actualizarUnProducto.getPrecioVentaField());
        Filter.setPrecioFilter(actualizarUnProducto.getCostoCompraField());

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
                try{
                    Categoria categoria = categoriaService.getCategoria(actualizarUnProducto.getCategoriaBox().getSelectedItem().toString());
                    Producto productoNuevo = new Producto(
                            actualizarUnProducto.getCodigoField().getText(),
                            actualizarUnProducto.getNombreField().getText(),
                            categoria,
                            Float.parseFloat(actualizarUnProducto.getCostoCompraField().getText()),
                            Float.parseFloat(actualizarUnProducto.getPrecioVentaField().getText()),
                            Integer.parseInt(actualizarUnProducto.getStockField().getText()));
                    FieldAnalyzer.todosLosCamposLlenos(actualizarUnProducto);
                    productoService.updateProducto(productoNuevo);
                }catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                }
                productosFrameUpdateTable();
            }
        });

        actualizarUnProducto.getCerrarButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                productosFrameUpdateTable();
                actualizarUnProducto.onCancel();
            }
        });

        // call onCancel() when cross is clicked
        actualizarUnProducto.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        actualizarUnProducto.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
               productosFrameUpdateTable();
               actualizarUnProducto.onCancel();
            }
        });

        // call onCancel() on ESCAPE
        actualizarUnProducto.getContentPane().registerKeyboardAction(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        productosFrameUpdateTable();
                        actualizarUnProducto.onCancel();
                    }
                }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );


        actualizarUnProducto.setVisible(true);
    }

    private void actualizarProductoPorCategoria(){/*
        actualizarPorCategoria = new ActualizarPorCategoria();
        categorias = categoriaService.getAll();

        for(Categoria categoria: categorias){
            actualizarPorCategoria.getCategoriaBox().addItem(categoria.getNombre());
        }
        JTextField porcentajeField = actualizarPorCategoria.getPorcentajeField();

        Filter.setDoubleFilter(porcentajeField);

        actualizarPorCategoria.getActualizarButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    FieldAnalyzer.campoLleno(porcentajeField);
                    FieldAnalyzer.limites(porcentajeField, -100, Integer.MAX_VALUE);
                } catch (OutOfBounds | EmptyFieldException of) {
                    JOptionPane.showMessageDialog(null, of.getMessage());
                }

                String cat = actualizarPorCategoria.getCategoriaBox().getSelectedItem().toString();
                Categoria categoria = categorias.stream().filter(categ -> categ.getNombre().equals(cat)).findFirst().get();

                String porcentaje = actualizarPorCategoria.getPorcentajeField().getText();
                try {

                    UpdateProductoPorcentajeDTO productoPorcentajeDTO = productoService.updatePrecioPorCategoria(categoria, new BigDecimal(porcentaje));
                    JOptionPane.showMessageDialog(null, "Cantidad de productos afectados: " + productoPorcentajeDTO.getCantProductos());

                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    throw new RuntimeException(ex.getMessage());
                }

            }
        });
        actualizarPorCategoria.getButtonOK().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarPorCategoria.onOK();
                productosFrameUpdateTable();
            }
        });

        actualizarPorCategoria.setVisible(true);*/
    }

    private void actualizacionGeneral(){
        /*
        ActualizarGeneral actualizarGeneral = new ActualizarGeneral();

        Filter.setDoubleFilter(actualizarGeneral.getPorcentajeField());

        actualizarGeneral.getActualizarButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UpdateProductoPorcentajeDTO productoPorcentajeDTO = new UpdateProductoPorcentajeDTO();
                try{
                    FieldAnalyzer.campoLleno(actualizarGeneral.getPorcentajeField());
                    FieldAnalyzer.limites(actualizarGeneral.getPorcentajeField(), -100, Integer.MAX_VALUE);
                    productoPorcentajeDTO=productoService.updatePrecioGeneral(new BigDecimal(actualizarGeneral.getPorcentajeField().getText()));
                }catch (OutOfBounds | EmptyFieldException of) {
                    JOptionPane.showMessageDialog(null, of.getMessage());
                }catch (RuntimeException re){
                    JOptionPane.showMessageDialog(null, re.getMessage());
                }

                JOptionPane.showMessageDialog(null, "Productos actualizados: " + productoPorcentajeDTO.getCantProductos());
                
            }
        });

        actualizarGeneral.setVisible(true);*/
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
                    categoriaService.deleteCategoria(categoria);
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
                    categoriaService.saveCategoria(categoria);
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

    private void productosFrameUpdateTable(){
        productosFrameUpdateTable("");
    }

    private void productosFrameUpdateTable(String filter) {
        // Limpiar todas las filas actuales del modelo de la tabla
        productsModel.setRowCount(0);

        productos = getAllProducto();

        String filterT = filter.toLowerCase();

        List<Producto> productosFiltrados = productos.stream()
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
        productos = null;
        productos = getAllProducto();
    }

    private List<Producto> getAllProducto() {
        return productoService.getAll();
    }

    private List<Categoria> getAllCategoria(){
        return categoriaService.getAll();
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

    private void existeProducto(Producto producto) throws RuntimeException {

        Producto p = productos.stream().filter(prod -> prod.getCodigo_barras().equals(producto.getCodigo_barras()) || prod.getNombre().equals(producto.getNombre())).findFirst().orElse(null);
        if(p != null) {
            if(p.getNombre().equals(producto.getNombre())) {
                throw new ProductNameExists("El producto con nombre: " + producto.getNombre() + " ya existe");
            }
        else if(p.getCodigo_barras().equals(producto.getCodigo_barras())){
                throw new ProductExistsInDataBase("El producto con código: " + producto.getCodigo_barras() + " ya existe");
            }
        }
    }


    protected void deshacer(){
        productoService.undo();
        productosFrameUpdateTable();
    }
    protected void deshacerTodo(){
        productoService.undoAll();
        productosFrameUpdateTable();
    }
    protected void save(){
        productoService.save();
    }

}
