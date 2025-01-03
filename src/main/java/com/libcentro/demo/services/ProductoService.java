package com.libcentro.demo.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.libcentro.demo.exceptions.InsufficientStockException;
import com.libcentro.demo.model.Categoria;
import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.dto.CategoriaDTO;
import com.libcentro.demo.model.dto.ProductoDTO;
import com.libcentro.demo.model.dto.ProductoPageDTO;
import com.libcentro.demo.repository.IcategoriaRepository;
import com.libcentro.demo.services.interfaces.IcategoriaService;
import com.libcentro.demo.services.interfaces.IhistorialService;
import com.libcentro.demo.utils.ProductosCSV;
import com.libcentro.demo.utils.command.*;
import com.libcentro.demo.view.productos.ProgresoProductos;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.libcentro.demo.model.Producto;
import com.libcentro.demo.repository.IproductoRepository;
import com.libcentro.demo.services.interfaces.IproductoService;

import javax.swing.*;

@Service
public class ProductoService implements IproductoService {

    private final IproductoRepository productoRepository;
    private final IhistorialService historialService;
    private final IcategoriaRepository categoriaRepository;
    private final IcategoriaService categoriaService;

    private final ProductosCSV productosCSV = new ProductosCSV () ;
    private final CommandInvoker commandInvoker = new CommandInvoker();

    @Autowired
    public ProductoService( IproductoRepository productoRepository,
                            IhistorialService historialService,
                            IcategoriaRepository categoriaRepository,
                            IcategoriaService categoriaService ){
        this.productoRepository = productoRepository;
        this.historialService = historialService;
        this.categoriaRepository = categoriaRepository;
        this.categoriaService = categoriaService;
    }

    @Override
    public List<ProductoDTO> getAll() {
        return productoRepository.findAll().stream().map (ProductoDTO::new).toList ();
    }

    @Override
    @Transactional
    public Producto saveProducto ( Producto x) {
       return productoRepository.saveAndFlush(x);
    }

    @Override
    public ProductoDTO crearProducto ( ProductoDTO productoDto ){
        if (productoRepository.existsById (productoDto.getCodigobarras ())) {
            throw new RuntimeException ("El producto con codigo: " + productoDto.getCodigobarras() + " ya existe.");
        }
        Producto producto = new Producto (productoDto);

        HistorialCosto historialCosto = historialService.crearHistorialCosto (producto,
                producto.getCosto_compra (),
                producto.getStock (),
                HistorialCosto.Estado.INICIAL);

        producto.getHistorial_costos ().add(historialCosto);

        HistorialPrecio historialPrecio = historialService.crearHistorialPrecio (producto,
                producto.getPrecio_venta ());

        producto.getHistorial_precios ().add(historialPrecio);

        commandInvoker.executeCommand (new AddProductCommand (this,producto));

        return productoDto;
    }

    @Override
    public boolean importarCSV(String path) {
        List<ProductoDTO> productosARC;
        try {
            productosARC = productosCSV.obtenerProductos(path, categoriaService);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ProgresoProductos progresoDialog = new ProgresoProductos(null, productosARC.size());

        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            int cuentaActualizados = 0;
            int cuentaCreados = 0;

            @Override
            protected Void doInBackground() throws Exception {
                SwingUtilities.invokeLater(() -> progresoDialog.mostrar());

                for (int i = 0; i < productosARC.size(); i++) {
                    ProductoDTO productoDTO = productosARC.get(i);
                    try {
                        Optional<Producto> productoOpt;
                        Producto producto;
                        Producto viejoProducto;
                        if ((productoOpt = productoRepository.findById(productoDTO.getCodigobarras())).isPresent ()) {
                            Categoria categoria = categoriaRepository.findByNombre (productoDTO.getCategoria().getNombre());
                            viejoProducto = new Producto (productoOpt.get ());
                            producto = productoOpt.get ();
                            producto.setNombre (productoDTO.getNombre());
                            producto.setCategoria (categoria);
                            producto.setCosto_compra (Math.round(productoDTO.getCosto_compra()*100d)/100d);
                            producto.setPrecio_venta (Math.round(productoDTO.getPrecio_venta()*100d) /100d);
                            producto.setStock (producto.getStock() + productoDTO.getStock());
                            System.out.println (producto.getStock ());
                            commandInvoker.executeCommand(new UpdateProductCommand(ProductoService.this, historialService, viejoProducto, producto));
                            cuentaActualizados++;
                        } else {
                            crearProducto (productoDTO);
                            cuentaCreados++;
                        }
                        publish(i + 1);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al procesar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int productosProcesados = chunks.get(chunks.size() - 1);
                progresoDialog.actualizarProgreso(productosProcesados, productosARC.size());
            }

            @Override
            protected void done() {
                progresoDialog.finalizar();
                try {
                    get();
                    JOptionPane.showMessageDialog(null, "Productos creados: " + cuentaCreados + " Productos actualizados: " + cuentaActualizados, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error durante el procesamiento: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    progresoDialog.cerrar();
                }
            }
        };
        worker.execute();
        return true;
    }


    @Override
    public void deleteProductoByCodigo(String codigo_barras){
        productoRepository.deleteById(codigo_barras);
    }

    @Override
    public ProductoPageDTO productosByPage ( int page, String filter, boolean sin_stock,int page_size,boolean categoria ){
        String filterT = "%" +  filter.toLowerCase() + "%";

        Page<Producto> productosPage = productoRepository.getProductosPage (PageRequest.of (page, page_size),filterT,sin_stock,categoria);

        return new ProductoPageDTO(productosPage.stream().map(ProductoDTO::new).toList(),productosPage.getTotalPages ());
    }

    @Override
    public void updateProducto( ProductoDTO productoActualizado) {
        Producto productoActual= productoRepository.findById(productoActualizado.getCodigobarras()).orElseThrow();

        Categoria categoria = categoriaRepository.findByNombre (productoActualizado.getCategoria ().getNombre ());

        Producto producto = new Producto (productoActualizado.getCodigobarras(),
                productoActualizado.getNombre(),
                categoria,
                productoActualizado.getCosto_compra (),
                productoActualizado.getPrecio_venta (),
                productoActualizado.getStock ()
                );
        commandInvoker.executeCommand(new UpdateProductCommand(this, historialService,productoActual,producto));
    }

    @Override
    public void updateProductosBy( CategoriaDTO categoriaDto, double porcentaje) {
        List<Producto> productosViejos;

        if(porcentaje == 0){
            throw new RuntimeException("Porcentaje no puede ser cero.");
        }

        if (categoriaDto == null) {
            productosViejos = productoRepository.findAll();
        } else {
            Categoria categoria = categoriaRepository.findByNombre (categoriaDto.getNombre ());
            productosViejos = productoRepository.findAllByCategoria(categoria);
        }

        List<Producto> productosNuevos = productosViejos.stream()
                .map(productoViejo -> {
                    Producto nuevoProducto = new Producto(productoViejo);
                    double nuevoPrecio = nuevoProducto.getPrecio_venta() + Math.round(nuevoProducto.getPrecio_venta() * porcentaje * 100d)/ 100d;
                    nuevoProducto.setPrecio_venta(nuevoPrecio);
                    return nuevoProducto;
                })
                .collect(Collectors.toList());


        commandInvoker.executeCommand(new UpdateProductsBy(this,productosNuevos,productosViejos, historialService));
    }

    @Override
    public ProductoDTO getProducto(String codigo_barras) throws ObjectNotFoundException{
        return productoRepository.findById(codigo_barras).stream()
                .map(ProductoDTO::new)
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException(Producto.class, "Producto no encontrado"));
    }

    @Override
    public ProductoDTO getProducto(String codigo_barras, int cantidad) {
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor que cero");
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        Producto producto= productoRepository.findById(codigo_barras).orElse(null);
        if(producto==null) {
            throw new RuntimeException("El producto con codigo: " + codigo_barras + " no existe.");
        }
        else if(cantidad > producto.getStock()){
            throw new InsufficientStockException("Cantidad insuficiente del producto: " + codigo_barras);
        }
        return new ProductoDTO(producto);
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
    public boolean save(){
        return commandInvoker.save();
    }

    @Override
    public List<ProductoDTO> getProductosByCantidad(int cantidad) {
        return productoRepository.findByStockLessThanEqual(cantidad).stream ().map (ProductoDTO::new).toList ();
    }

    @Override
    public void venderProducto ( ProductoDTO productoDTO, int cantidad ){
        Optional<Producto> producto = productoRepository.findById(productoDTO.getCodigobarras());
        producto.ifPresent(p -> {
                p.setStock (productoDTO.getStock () - cantidad);
                HistorialCosto historialCosto = historialService.findHistorialInicial (p);
                if(historialCosto.getCantidad () > historialCosto.getCantidad () + cantidad){
                    historialCosto.setCantidad_vendida (cantidad);
                }else{
                    int falta = historialCosto.getCantidad () - historialCosto.getCantidad_vendida ();
                    int resto = cantidad-falta;
                    HistorialCosto newHistorial= historialService.findNext (p);
                    if(newHistorial != null){
                            newHistorial.setCantidad_vendida (resto);
                            newHistorial.setEstado (HistorialCosto.Estado.INICIAL);
                            historialService.save(newHistorial);
                    }

                    historialCosto.setCantidad_vendida (historialCosto.getCantidad ());
                    historialCosto.setEstado (HistorialCosto.Estado.INHABILITADO);
                    historialService.save(historialCosto);
                }

                productoRepository.save(p);});
    }

    @Override
    public void actualizarCategorias ( List<ProductoDTO> productosSeleccionados, String string ){
            Categoria categoria = categoriaRepository.findByNombre (string);

            productosSeleccionados.forEach(p -> {
                Producto productoActual = productoRepository.findById (p.getCodigobarras()).orElse(null);
                Producto producto = new Producto (p.getCodigobarras(),
                        p.getNombre(),
                        categoria,
                        p.getCosto_compra (),
                        p.getPrecio_venta (),
                        p.getStock ()
                );

                if(producto==null) { throw  new RuntimeException ("Producto no encontrado, código: " + p.getCodigobarras()); }

                commandInvoker.executeCommand (new UpdateProductCommand (this,historialService,productoActual,producto));
            });

    }

    @Override
    public void updatePrecio ( List<ProductoDTO> productosDTO, double porcentaje ){

        productosDTO.forEach(p -> {
            Producto productoActual = productoRepository.findById (p.getCodigobarras()).orElse(null);

            if(productoActual == null) throw  new RuntimeException ("Producto no encontrado");

            Producto productoNuevo = new Producto (productoActual);
            productoNuevo.setPrecio_venta(Math.round((p.getPrecio_venta() + p.getPrecio_venta() * porcentaje / 100) * 100.0) / 100.0);

            commandInvoker.executeCommand (new UpdateProductCommand (this,historialService,productoActual,productoNuevo));
            });
    }

}
