package com.libcentro.demo.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.libcentro.demo.exceptions.InsufficientStockException;
import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.dto.CategoriaDTO;
import com.libcentro.demo.model.dto.ProductoDTO;
import com.libcentro.demo.services.interfaces.IhistorialService;
import com.libcentro.demo.utils.ProductosCSV;
import com.libcentro.demo.utils.command.*;
import com.libcentro.demo.view.productos.ProgresoProductos;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libcentro.demo.model.Producto;
import com.libcentro.demo.repository.IproductoRepository;
import com.libcentro.demo.services.interfaces.IproductoService;
import org.springframework.transaction.support.TransactionTemplate;

import javax.swing.*;

@Service
public class ProductoService implements IproductoService {

    @Autowired
    private IproductoRepository productoRepository;
    @Autowired
    private IhistorialService historialService;

    @Autowired
    private TransactionTemplate transactionTemplate;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    ProductosCSV productosCSV;

    private final CommandInvoker commandInvoker = new CommandInvoker();
    @Autowired
    private CategoriaService categoriaService;

    @Override
    public List<Producto> getAll() {
        return productoRepository.findAll();
    }

    @Override
    @Transactional
    public Producto saveProducto( Producto x) {
       return productoRepository.saveAndFlush(x);
    }

    @Override
    public Producto crearProducto ( ProductoDTO productoDto ){


        if (productoRepository.existsById (productoDto.getCodigobarras ())) {
            throw new RuntimeException ("El producto con codigo: " + productoDto.getCodigobarras() + " ya existe.");
        }

        Producto producto = new Producto (productoDto);
        LocalDate fechaActual = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaActual.format(formatter);

        HistorialCosto historialCosto = new HistorialCosto();
        historialCosto.setProducto(producto);
        historialCosto.setCosto_compra (productoDto.getCosto_compra());
        historialCosto.setCantidad (producto.getStock ());
        historialCosto.setFecha (fechaFormateada);

        producto.getHistorial_costos ().add(historialCosto);

        HistorialPrecio historialPrecio = new HistorialPrecio();
        historialPrecio.setProducto(producto);
        historialPrecio.setPrecio_venta (productoDto.getPrecio_venta());
        historialPrecio.setFecha (fechaFormateada);

        producto.getHistorial_precios ().add(historialPrecio);

        commandInvoker.executeCommand (new AddProductCommand (this,producto));

        return producto;
    }





    @Override
    public boolean importarCSV(String path) {
        List<ProductoDTO> productosARC;

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
                    ProductoDTO productoDTO = productosARC.get(i);
                    try {
                        Producto producto;
                        if (productos.stream().anyMatch (p -> p.getCodigobarras ().equals (productoDTO.getCodigobarras ()))) {
                            producto = getProducto(productoDTO.getCodigobarras());
                            producto.setNombre (productoDTO.getNombre());
                            producto.setCategoria (productoDTO.getCategoria());
                            producto.setCosto_compra (productoDTO.getCosto_compra());
                            producto.setPrecio_venta (productoDTO.getPrecio_venta());
                            producto.setStock (producto.getStock() + productoDTO.getStock());
                            commandInvoker.executeCommand(new UpdateProductCommand(ProductoService.this, historialService, getProducto(producto.getCodigobarras ()), producto));
                            cuentaActualizados++;
                        } else {
                            producto = crearProducto(productoDTO);
                            commandInvoker.executeCommand(new AddProductCommand(ProductoService.this, producto));
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
        productoRepository.deleteById(x.getCodigobarras ());
        productoRepository.flush();
    }

    @Override
    public void deleteProductoByCodigo(String codigo_barras){
        productoRepository.deleteById(codigo_barras);
    }
    @Override
    public void deleteProducto(String codigo_barras){
        Producto producto = productoRepository.getProductoWithHistorialPrecioAndHistorialCosto(codigo_barras);
        commandInvoker.executeCommand(new DeleteProductCommand(this, producto,historialService));
    }

    @Override
    public void updateProducto( ProductoDTO productoActualizado) {
        Producto productoActual= productoRepository.findById(productoActualizado.getCodigobarras()).orElse(null);
        assert productoActual != null;


        Producto producto = new Producto (productoActualizado.getCodigobarras(),
                productoActualizado.getNombre(),
                productoActualizado.getCategoria (),
                productoActualizado.getCosto_compra (),
                productoActualizado.getPrecio_venta (),
                productoActualizado.getStock ()
                );

        commandInvoker.executeCommand(new UpdateProductCommand(this, historialService,productoActual,producto));
    }


    @Override
    public void updateProductosBy( CategoriaDTO categoria, double porcentaje) {
        List<Producto> productosViejos;

        if(porcentaje == 0){
            throw new RuntimeException("Porcentaje no puede ser cero.");
        }

        if (categoria == null) {
            productosViejos = productoRepository.findAll();
        } else {
            productosViejos = productoRepository.findAllByCategoria(categoria);
        }


        List<Producto> productosNuevos = productosViejos.stream()
                .map(productoViejo -> {
                    Producto nuevoProducto = new Producto(productoViejo); // Asumimos que Producto tiene un constructor copia
                    double nuevoPrecio = nuevoProducto.getPrecio_venta() + (nuevoProducto.getPrecio_venta() * porcentaje);
                    nuevoProducto.setPrecio_venta(nuevoPrecio);
                    return nuevoProducto;
                })
                .collect(Collectors.toList());


        commandInvoker.executeCommand(new UpdateProductsBy(this,productosNuevos,productosViejos, historialService));
    }

    @Override
    public Producto getProducto(String codigo_barras){
        return productoRepository.findById(codigo_barras).orElse(null);
    }

    @Override
    public Producto getProducto(String codigo_barras, int cantidad) {
        Producto producto= productoRepository.findById(codigo_barras).orElse(null);
        if(producto==null) {
            throw new RuntimeException("El producto con codigo: " + codigo_barras + " no existe.");
        }
        else if(cantidad >= producto.getStock()){
            throw new InsufficientStockException("Cantidad insuficiente del producto: " + codigo_barras);
        }
        return producto;
    }

   /* @Override
    public void venderProducto(Producto producto, int cantidad){
        Producto productoExistente = productoRepo.findById(producto.getCodigo_barras())
                .orElseThrow(() -> new ObjectNotFoundException(Producto.class, producto.getCodigo_barras()));

        productoExistente.setStock(producto.getStock()-cantidad);
        productoRepo.save(productoExistente);
    }
*/

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

    @Override
    public List<Producto> getProductosByCantidad(int cantidad) {
        return productoRepository.findByStockLessThanEqual(cantidad);
    }


    public HistorialPrecio anadirHistorialPrecio(Producto producto){
        HistorialPrecio historialPrecio= new HistorialPrecio(producto,producto.getPrecio_venta());
        historialService.save(historialPrecio);
        return historialPrecio;
    }




}
