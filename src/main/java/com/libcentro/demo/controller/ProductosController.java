package com.libcentro.demo.controller;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.regex.Pattern;

import com.libcentro.demo.exceptions.EmptyFieldException;
import com.libcentro.demo.exceptions.OutOfBounds;
import com.libcentro.demo.model.dto.CategoriaDTO;
import com.libcentro.demo.model.dto.ProductoDTO;
import com.libcentro.demo.services.interfaces.IcategoriaService;
import com.libcentro.demo.utils.FieldAnalyzer;
import com.libcentro.demo.utils.filters.Filter;
import com.libcentro.demo.view.ConfirmarDialog;
import com.libcentro.demo.view.productos.*;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;

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

    //Services
    private final IproductoService productoService;
    private final IcategoriaService categoriaService;

    //Controllers
    ViewController viewController;
    ProductosFrame productosFrame;

    //Pagina
    int page=0;

    //Productos
    List<ProductoDTO> productos;
    List<CategoriaDTO> categorias;

    //Vistas
    AgregarProducto agregarProducto;
    ActualizarUnProducto actualizarUnProducto;
    ActualizarPorCategoria actualizarPorCategoria;
    AgregarCategoria agregarCategoria;

    //Modelos de tabla
    DefaultTableModel productsModel;
    DefaultTableModel categoriasModel;


    @Autowired
    public ProductosController(@Lazy ViewController viewController, IproductoService productoService, IcategoriaService categoriaService) {
        this.viewController = viewController;
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    public void openProductosFrame() {
        this.page=0;

        if (productosFrame == null) {
            productosFrame = new ProductosFrame();
            productosFrameAddListeners();

            // Añadir un mapeo para la tecla F5
            productosFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                    .put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "refreshTable");

            // Asignar una acción a la tecla F5
            productosFrame.getRootPane().getActionMap().put("refreshTable", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    productosFrameUpdateTable();
                }
            });
        }


        this.productsModel = (DefaultTableModel) productosFrame.getTable().getModel();
        productosFrameUpdateTable();

        categorias= getAllCategoria();
        productosFrame.getPageCount ().setText (this.page + 1 + "");
        pagina (0);



        productosFrame.setVisible(true);
        productosFrame.setState(Frame.NORMAL);
        productosFrame.toFront();
        productosFrame.requestFocus();

    }

    private void productosFrameAddListeners(){
        productosFrame.getSinStockCheckBox().addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                productosFrameUpdateTable();
            }
        });

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
        productosFrame.getImportarCsvButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                importarCSV();
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
                ConfirmarDialog confirmarDialog = new ConfirmarDialog("Confirmar deshacer todo");
                confirmarDialog.setVisible(true);

                if(confirmarDialog.isAceptar()) {
                    deshacerTodo();
                }
            }
        });

        productosFrame.getActualizarTablaButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productosFrameUpdateTable();
                pagina (0);
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
        productosFrame.getEliminarProductoButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    ConfirmarDialog cd = new ConfirmarDialog ("Borrar? esta accion no se puede deshacer");
                    cd.setVisible(true);
                    if(cd.isAceptar ()) {
                        eliminarProducto ();
                        productosFrameUpdateTable ();
                    }
            }
        });

        productosFrame.getAnteriorButton ().addActionListener (new ActionListener(){
            @Override
            public void actionPerformed ( ActionEvent e ){
                pagina(-1);
            }
        });
        productosFrame.getSiguienteButton ().addActionListener (new ActionListener(){
            @Override
            public void actionPerformed ( ActionEvent e ){
                pagina(+1);
            }
        });
    }

    private void pagina ( int i ){
        productosFrame.getAnteriorButton ().setEnabled(this.page + i > 0);
        this.page += i;

        productosFrame.getPageCount ().setText (this.page + 1 + "");
        productosFrameUpdateTable(productosFrame.getBuscarField ().getText ());

        productosFrame.getSiguienteButton().setEnabled(this.productos.size () >= 25);
    }

    //Agregar
    private void agregarProducto() {
        categorias = getAllCategoria();
        agregarProducto = new AgregarProducto();

        System.out.println(categorias);
        for(CategoriaDTO categoria : categorias) {
            agregarProducto.getCategoriaBox().addItem(categoria.getNombre());

        }
        agregarProducto.getButtonOK().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    FieldAnalyzer.todosLosCamposLlenos(agregarProducto);
                    CategoriaDTO categoriaDTO = categorias.stream()
                            .filter(categoria -> categoria.getNombre().equals(agregarProducto.getCategoriaBox().getSelectedItem().toString()))
                            .findFirst().orElseThrow();
                    ProductoDTO producto = new ProductoDTO(
                            agregarProducto.getCodigoField().getText(),
                            agregarProducto.getNombreField().getText(),
                            categoriaDTO,
                            Float.parseFloat(agregarProducto.getCostoField().getText()),
                            Float.parseFloat(agregarProducto.getPrecioField().getText()),
                            Integer.parseInt(agregarProducto.getCantidadField().getText())
                    );

                        productoService.crearProducto(producto);

                    }catch(RuntimeException ex) {
                        JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                        throw new RuntimeException(ex);
                    }
                    agregarProducto.onOK();
                    productosFrameUpdateTable();
                }
        });


        agregarProducto.setVisible(true);

    }

    private void importarCSV(){
        ImportarCSV importarCSV= new ImportarCSV();

        importarCSV.getBuscarButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = jFileChooser.showOpenDialog(null);
                if(result == JFileChooser.APPROVE_OPTION){
                    importarCSV.getLocationField().setText(jFileChooser.getSelectedFile().getAbsolutePath());
                } else{
                    JOptionPane.showMessageDialog(null,"No se seleccionó archivo","Error",JOptionPane.INFORMATION_MESSAGE);
                    throw new RuntimeException("No se seleccionó archivo");
                }
            }
        });

        importarCSV.getSubirButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    productoService.importarCSV(importarCSV.getLocationField().getText());
                }catch (RuntimeException ex){
                    JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException(ex.getMessage());
                }
                productosFrameUpdateTable();
            }
        });
        importarCSV.getButtonOK().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productosFrameUpdateTable();
            }
        });
        importarCSV.getButtonCancel().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                productosFrameUpdateTable();
            }
        });
        importarCSV.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                productosFrameUpdateTable();
            }
        });

        importarCSV.setVisible(true);
    }

    //Actualizacion
    private void actualizarUnProducto(){
        categorias= getAllCategoria();
        actualizarUnProducto = new ActualizarUnProducto();
        final ProductoDTO[] producto = new ProductoDTO[1];

        for(CategoriaDTO categoria : categorias) {
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
                }catch (ObjectNotFoundException ex){
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
                    CategoriaDTO categoria = categoriaService.getCategoria(actualizarUnProducto.getCategoriaBox().getSelectedItem().toString());
                    ProductoDTO productoNuevo = new ProductoDTO (
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
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(null,"Producto actualizado","Éxito",JOptionPane.INFORMATION_MESSAGE);
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

    private void actualizarProductoPorCategoria(){
        actualizarPorCategoria = new ActualizarPorCategoria();
        categorias = categoriaService.getAll();

        for(CategoriaDTO categoria: categorias){
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
                CategoriaDTO categoria = categorias.stream().filter(categ -> categ.getNombre().equals(cat)).findFirst().get();

                float porcentaje = Float.parseFloat(actualizarPorCategoria.getPorcentajeField().getText())/100;
                try {
                    productoService.updateProductosBy(categoria,porcentaje);
                    JOptionPane.showMessageDialog(null,"Productos con categoria " + categoria.getNombre() + " actualizados","Actualización exitosa" ,JOptionPane.INFORMATION_MESSAGE);
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

        actualizarPorCategoria.setVisible(true);
    }

    private void actualizacionGeneral(){
        ActualizarGeneral actualizarGeneral = new ActualizarGeneral();

        Filter.setDoubleFilter(actualizarGeneral.getPorcentajeField());

        actualizarGeneral.getActualizarButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    FieldAnalyzer.campoLleno(actualizarGeneral.getPorcentajeField());
                    FieldAnalyzer.limites(actualizarGeneral.getPorcentajeField(), -100, Integer.MAX_VALUE);
                    float porcentaje= Float.parseFloat(actualizarGeneral.getPorcentajeField().getText())/100;
                    productoService.updateProductosBy(null,porcentaje);
                    JOptionPane.showMessageDialog(null,"Productos actualizados","Actualización exitosa",JOptionPane.INFORMATION_MESSAGE);
                }catch (RuntimeException of) {
                    JOptionPane.showMessageDialog(null, of.getMessage());
                }
                productosFrameUpdateTable();
            }
        });

        actualizarGeneral.setVisible(true);
    }

    //Eliminación
    private void eliminarProducto(){
       int[] fila= productosFrame.getTable().getSelectedRows();
       if(fila.length==0){
           JOptionPane.showMessageDialog(null,"Seleccione un producto en la tabla","Error",JOptionPane.ERROR_MESSAGE);
           throw new RuntimeException("Seleccione un producto en la tabla");
       }
       try {
           for(Integer i: fila){
               productoService.deleteProductoByCodigo (productosFrame.getTable().getValueAt(i, 0).toString());
           }

       }catch (RuntimeException e){
           JOptionPane.showMessageDialog(null, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
       }
    }
    //Si bien dice agregar categoria aquí tambien se maneja la eliminación
    private void agregarCategoria() {
        categorias = getAllCategoria();
        agregarCategoria = new AgregarCategoria();

        categoriasModel = (DefaultTableModel) agregarCategoria.getTablaCategorias().getModel();
        ListSelectionModel categoriasSelectionModel = (ListSelectionModel) agregarCategoria.getTablaCategorias().getSelectionModel();

        for(CategoriaDTO categoria : categorias) {
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
                CategoriaDTO categoria = categorias.stream()
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
                CategoriaDTO categoria= new CategoriaDTO ();
                categoria.setNombre (agregarCategoria.getCategoriaField().getText());
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


       this.productos = productoService.productosByPage(this.page,filter,productosFrame.getSinStockCheckBox().isSelected());

        // Agregar los productos al modelo de la tabla
        for (ProductoDTO producto : productos) {
            addProductoToTable(producto);
        }

    }

    private List<CategoriaDTO> getAllCategoria(){
        return categoriaService.getAll();
    }
    private void addProductoToTable(ProductoDTO producto){

        productsModel.addRow(new Object[]{
                producto.getCodigobarras(),
                producto.getNombre(),
                producto.getCategoria().getNombre (),
                producto.getStock(),
                producto.getCosto_compra(),
                producto.getPrecio_venta()
        });
    }
    private void deshacer(){
        productoService.undo();
        productosFrameUpdateTable();
    }
    private void deshacerTodo(){
        productoService.undoAll();
        productosFrameUpdateTable();
    }
    private void save(){
        productoService.save();
    }

}
