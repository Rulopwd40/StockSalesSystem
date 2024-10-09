package com.libcentro.demo.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.libcentro.demo.exceptions.InsufficientStockException;
import com.libcentro.demo.model.Categoria;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.services.interfaces.IhistorialCostosService;
import com.libcentro.demo.services.interfaces.IhistorialPreciosService;
import com.libcentro.demo.utils.ProductosCSV;
import com.libcentro.demo.utils.command.*;
import com.libcentro.demo.view.productos.ProgresoProductos;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libcentro.demo.model.Producto;
import com.libcentro.demo.repository.IproductoRepository;
import com.libcentro.demo.services.interfaces.IproductoService;

import javax.swing.*;

@Service
public class ProductoService implements IproductoService {
    @Autowired
    private IproductoRepository productoRepo;
    @Autowired
    private IhistorialCostosService historialCostosService;
    @Autowired
    private IhistorialPreciosService historialPreciosService;

    ProductosCSV productosCSV = new ProductosCSV();


    private final CommandInvoker commandInvoker = new CommandInvoker();
    @Autowired
    private CategoriaService categoriaService;

    @Override
    public List<Producto> getAll() {
        return productoRepo.findAll();
    }

    @Override
    public void saveProducto(Producto x) {
        productoRepo.save(x);
    }
    @Override
    public Producto crearProducto(Producto producto) {

        Producto existingProducto = productoRepo.findById(producto.getCodigo_barras()).orElse(null);

        if (existingProducto != null) {
            throw new RuntimeException("El producto con codigo: " + producto.getCodigo_barras() + " ya existe.");
        }
        commandInvoker.executeCommand(new AddProductCommand(this, producto, historialPreciosService, historialCostosService));


        return producto;
    }


    @Override
    public boolean importarCSV(String path) {
        List<Producto> productosARC;

        // Obtener productos desde el archivo CSV
        try {
            productosARC = productosCSV.obtenerProductos(path, categoriaService);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Obtener los productos ya existentes
        List<Producto> productos = getAll();

        // Inicializar el diálogo de progreso con la cantidad total de productos a procesar
        ProgresoProductos progresoDialog = new ProgresoProductos(null, productosARC.size());

        // Crear un SwingWorker para procesar los productos en segundo plano
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            int cuentaActualizados = 0;
            int cuentaCreados = 0;

            @Override
            protected Void doInBackground() throws Exception {
                // Mostrar el diálogo de progreso
                SwingUtilities.invokeLater(() -> progresoDialog.mostrar());

                for (int i = 0; i < productosARC.size(); i++) {
                    Producto producto = productosARC.get(i);
                    try {
                        if (productos.contains(producto)) {
                            updateProductoCSV(producto); // Actualizar producto existente
                            cuentaActualizados++;
                        } else {
                            crearProducto(producto); // Crear nuevo producto
                            cuentaCreados++;
                        }
                        // Publicar el progreso
                        publish(i + 1); // Actualizar la barra de progreso
                    } catch (Exception e) {
                        // Manejo de errores dentro del ciclo
                        JOptionPane.showMessageDialog(null, "Error al procesar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                // Actualizar la barra de progreso con el progreso actual
                int productosProcesados = chunks.get(chunks.size() - 1);
                progresoDialog.actualizarProgreso(productosProcesados, productosARC.size());
            }

            @Override
            protected void done() {
                // Finalizar el diálogo cuando el proceso esté completo
                progresoDialog.finalizar();
                try {
                    get(); // Asegurar que no haya excepciones
                    JOptionPane.showMessageDialog(null, "Productos creados: " + cuentaCreados + " Productos actualizados: " + cuentaActualizados, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error durante el procesamiento: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    progresoDialog.cerrar();
                }
            }
        };

        // Ejecutar el SwingWorker
        worker.execute();
        return true;
    }

    @Override
    public void deleteProducto(Producto x) {
        productoRepo.deleteById(x.getCodigo_barras());
        productoRepo.flush();
    }

    @Override
    public void deleteProductoByCodigo(String codigo_barras){
        productoRepo.deleteById(codigo_barras);
    }
    @Override
    public void deleteProducto(String codigo_barras){
        Producto producto = productoRepo.getProductoWithHistorialPrecioAndHistorialCosto(codigo_barras);
        commandInvoker.executeCommand(new DeleteProductCommand(this, producto,historialPreciosService,historialCostosService));
    }

    @Override
    public void updateProductoCSV(Producto producto){

        Producto productoObtenido = getProducto(producto.getCodigo_barras());
        producto.setStock(producto.getStock()+productoObtenido.getStock());
        updateProducto(producto);
    }
    @Override
    public void updateProducto(Producto productoActualizado) {
        Producto productoActual= productoRepo.findById(productoActualizado.getCodigo_barras()).orElse(null);
        assert productoActual != null;
        productoActualizado.setCosto_inicial(productoActual.getCosto_inicial());
        commandInvoker.executeCommand(new UpdateProductCommand(this, historialCostosService, historialPreciosService,productoActual,productoActualizado));
    }


    @Override
    public void updateProductosBy(Categoria categoria, float porcentaje) {
        List<Producto> productosViejos;

        if(porcentaje == 0){
            throw new RuntimeException("Porcentaje no puede ser cero.");
        }
        // Obtén los productos según la categoría (o todos si la categoría es null)
        if (categoria == null) {
            productosViejos = productoRepo.findAll();
        } else {
            productosViejos = productoRepo.findAllByCategoria(categoria);
        }

        // Crea una nueva lista de productos (productosNuevos) a partir de productosViejos
        List<Producto> productosNuevos = productosViejos.stream()
                .map(productoViejo -> {
                    Producto nuevoProducto = new Producto(productoViejo); // Asumimos que Producto tiene un constructor copia
                    float nuevoPrecio = nuevoProducto.getPrecio_venta() + (nuevoProducto.getPrecio_venta() * porcentaje);
                    nuevoProducto.setPrecio_venta(nuevoPrecio);
                    return nuevoProducto;
                })
                .collect(Collectors.toList());

        // Guarda los productos actualizados en la base de datos
        commandInvoker.executeCommand(new UpdateProductsBy(this,productosNuevos,productosViejos, historialPreciosService));
    }

    @Override
    public Producto getProducto(String codigo_barras){
        return productoRepo.findById(codigo_barras).orElse(null);
    }

    @Override
    public Producto getProducto(String codigo_barras, int cantidad) {
        Producto producto= productoRepo.findById(codigo_barras).orElse(null);
        if(producto==null) {
            throw new RuntimeException("El producto con codigo: " + codigo_barras + " no existe.");
        }
        else if(cantidad >= producto.getStock()){
            throw new InsufficientStockException("Cantidad insuficiente del producto: " + codigo_barras);
        }
        return producto;
    }

    @Override
    public void venderProducto(Producto producto, int cantidad){
        Producto productoExistente = productoRepo.findById(producto.getCodigo_barras())
                .orElseThrow(() -> new ObjectNotFoundException(Producto.class, producto.getCodigo_barras()));

        productoExistente.setStock(producto.getStock()-cantidad);
        productoRepo.save(productoExistente);
    }


    @Override
    public void undo(){
        try{
            commandInvoker.undoCommand();
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void undoAll(){
        try {
            commandInvoker.undoAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(){
        commandInvoker.save();
    }


    public HistorialPrecio anadirHistorialPrecio(Producto producto){
        HistorialPrecio historialPrecio= new HistorialPrecio(producto,producto.getPrecio_venta());
        historialPreciosService.save(historialPrecio);
        return historialPrecio;
    }




}
