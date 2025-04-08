package com.libcentro.demo.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.libcentro.demo.exceptions.InsufficientStockException;
import com.libcentro.demo.model.*;
import com.libcentro.demo.model.dto.CategoriaDTO;
import com.libcentro.demo.model.dto.PageDTO;
import com.libcentro.demo.model.dto.ProductoDTO;
import com.libcentro.demo.services.interfaces.*;
import com.libcentro.demo.utils.ProductosCSV;
import com.libcentro.demo.utils.command.*;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.libcentro.demo.repository.IproductoRepository;

import javax.swing.*;


@Service
public class ProductoService implements IproductoService {

    private final IproductoRepository productoRepository;
    private final IhistorialService historialService;
    private final IcategoriaService categoriaService;
    private final ialertService alertService;

    private final ProductosCSV productosCSV = new ProductosCSV () ;
    private final CommandInvoker commandInvoker = new CommandInvoker();

    @Autowired
    public ProductoService( IproductoRepository productoRepository,
                            IhistorialService historialService,
                            IcategoriaService categoriaService,
                            ialertService alertService ){
        this.productoRepository = productoRepository;
        this.historialService = historialService;
        this.categoriaService = categoriaService;
        this.alertService = alertService;
    }

    @Override
    public List<ProductoDTO> getAll() {
        return productoRepository.findAll().stream()
                .filter(producto -> !producto.isEliminado())
                .map(ProductoDTO::new)
                .toList();
    }

    @Override
    @Transactional
    public Producto saveProducto ( Producto x) {
       return productoRepository.saveAndFlush(x);
    }

    @Override
    public ProductoDTO crearProducto ( ProductoDTO productoDto ){
        Optional<Producto> productoOptional = productoRepository.findById (productoDto.getCodigobarras ());
        Producto producto;

        if(productoOptional.isPresent ()) {
            producto = productoOptional.get ();
            if ( producto.isEliminado () ) {
                updateProducto (productoDto);
                return productoDto;
            }
            else throw new RuntimeException ("El producto con codigo: " + productoDto.getCodigobarras() + " ya existe.");
        }
        producto = new Producto (productoDto);

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

        IprogressService<ProductoDTO> progressService = new ProgressService<>(null, productosARC.size());

        progressService.ejecutarProceso(productosARC, productoDTO -> {
            Optional<Producto> productoOpt;
            Producto producto;
            Producto viejoProducto;

            try {
                if ((productoOpt = productoRepository.findById(productoDTO.getCodigobarras())).isPresent()) {
                    Categoria categoria= categoriaService.getCategoria (productoDTO.getCategoria().getNombre());
                    viejoProducto = new Producto(productoOpt.get());
                    producto = productoOpt.get();
                    producto.setNombre(productoDTO.getNombre());
                    producto.setCategoria(categoria);
                    producto.setCosto_compra(Math.round(productoDTO.getCosto_compra() * 100d) / 100d);
                    producto.setPrecio_venta(Math.round(productoDTO.getPrecio_venta() * 100d) / 100d);
                    producto.setStock(producto.getStock() + productoDTO.getStock());
                    producto.setEliminado (false);
                    commandInvoker.executeCommand(new UpdateProductCommand(ProductoService.this, historialService, viejoProducto, producto));
                } else {
                    crearProducto(productoDTO);
                }
            } catch (Exception e) {
                throw new RuntimeException("Error al procesar producto: " + e.getMessage());
            }
        });

        return true;
    }

    @Override
    public void deleteProductoByCodigo(String codigo_barras){

       Producto producto =  productoRepository.findById (codigo_barras).orElseThrow (() -> new RuntimeException ("Producto no encontrado"));
       producto.setEliminado (true);
       historialService.deleteAllByCodigo (producto.getCodigobarras ());
       productoRepository.save(producto);
    }

    @Override
    public PageDTO<ProductoDTO> productosByPage ( int page, String filter, boolean sin_stock, int page_size, boolean categoria ){
        String filterT = "%" +  filter.toLowerCase() + "%";

        Page<Producto> productosPage = productoRepository.getProductosPage (PageRequest.of (page, page_size),filterT,sin_stock,categoria);

        return new PageDTO<> (productosPage.stream ().filter (producto -> !producto.isEliminado ()).map (ProductoDTO::new).toList (), productosPage.getTotalPages ());
    }

    @Override
    public void updateProducto( ProductoDTO productoActualizado) {
        Producto productoActual= productoRepository.findById(productoActualizado.getCodigobarras()).orElseThrow(() -> new RuntimeException ("Producto no encontrado"));

        Categoria categoria = categoriaService.getCategoria (productoActualizado.getCategoria ().getNombre ());

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
    public void updateProductosBy(CategoriaDTO categoriaDto, double porcentaje){
        List<Producto> productosViejos;

        if ( porcentaje == 0 ) {
            throw new RuntimeException ("Porcentaje no puede ser cero.");
        }
        if ( porcentaje >= 15000 ) {
            throw new RuntimeException ("Actualización de porcentaje excesiva, proceso abortado");
        }

        if ( categoriaDto == null ) {
            productosViejos = productoRepository.findAll ().stream ().filter (producto -> !producto.isEliminado ()).toList ();
        } else {
            Categoria categoria = categoriaService.getCategoria (categoriaDto.getNombre ());
            productosViejos = productoRepository.findAllByCategoriaAndEliminadoFalse (categoria);
        }

        List<Producto> productosNuevos = new ArrayList<> ();
        int errCount = 0;

        for (Producto productoViejo : productosViejos) {
            try {
                Producto nuevoProducto = new Producto (productoViejo);
                double nuevoPrecio = nuevoProducto.getPrecio_venta () + Math.round (nuevoProducto.getPrecio_venta () * porcentaje * 100d) / 100d;

                // Verificar el precio antes de actualizar
                if ( nuevoPrecio >= 999999999999d ) {
                    throw new RuntimeException ("Exceso en limite de memoria en el producto: " + nuevoProducto.getCodigobarras ());
                }

                nuevoProducto.setPrecio_venta (nuevoPrecio);
                productosNuevos.add (nuevoProducto);
            } catch (RuntimeException e) {
                errCount++;
            }
        }
        alertService.executeWithDialog (() -> {
                    return commandInvoker.executeCommand (new UpdateProductsBy (this, productosNuevos, productosViejos, historialService));
                },
                () -> {
                    return;
                },
                () ->{
                    throw new RuntimeException ("Error al actualizar");
                }
                );

        commandInvoker.executeCommand (new UpdateProductsBy (this, productosNuevos, productosViejos, historialService));
        if(errCount != 0) JOptionPane.showMessageDialog (null,"Error al procesar " + errCount + " productos.","Error",JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public ProductoDTO getProductoDTO ( String codigo_barras) throws ObjectNotFoundException{
        return productoRepository.findById(codigo_barras).stream()
                .map(ProductoDTO::new)
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException(Producto.class, "Producto no encontrado"));
    }

    @Override
    public ProductoDTO getProductoDTO ( String codigo_barras, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        Producto producto= productoRepository.findById(codigo_barras).orElse(null);
        if(producto==null || producto.isEliminado ()) {
            throw new RuntimeException ("El producto con codigo: " + codigo_barras + " no existe.");
        }
        else if(cantidad > producto.getStock()){
            throw new InsufficientStockException("Cantidad insuficiente del producto: " + codigo_barras);
        }
        return new ProductoDTO(producto);
    }

    @Override
    public Producto getProducto ( String codigobarras ){
        Producto producto = productoRepository.findById(codigobarras).orElseThrow(() -> new ObjectNotFoundException(Producto.class, "Producto no encontrado"));
        if (producto.isEliminado ()) throw new RuntimeException ("El producto fue eliminado anteriormente");
        return producto;
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
        return productoRepository.findByStockLessThanEqualAndEliminadoFalse (cantidad).stream ().map (ProductoDTO::new).toList ();
    }

    @Override
    public void venderProducto(ProductoDTO productoDTO, int cantidadAVender) {
        Optional<Producto> producto = productoRepository.findById(productoDTO.getCodigobarras());
        producto.ifPresent(p -> {
            // Actualizar el stock del producto
            p.setStock(productoDTO.getStock() - cantidadAVender);

            // Llamar a la función recursiva para procesar la venta
            venderRecursivo(p, cantidadAVender, null);

            // Guardar el producto actualizado
            productoRepository.save(p);
        });
    }

    private void venderRecursivo(Producto producto, int cantidadAVender, HistorialCosto historialCostoAnterior) {
        // Obtener el historial de costos inicial
        HistorialCosto historialCosto = (historialCostoAnterior == null) ? historialService.findHistorialInicial(producto) : historialCostoAnterior;
        // Si hay suficiente cantidad en el historial de costos
        while (cantidadAVender > 0 && historialCosto != null) {
            int cantidadDisponible = historialCosto.getCantidad() - historialCosto.getCantidad_vendida();

            if (cantidadDisponible > 0) {
                if (cantidadAVender <= cantidadDisponible) {
                    System.out.println ("1");
                    // Si la cantidad que se desea vender es menor o igual a lo disponible en el historial actual
                    historialCosto.setCantidad_vendida(historialCosto.getCantidad_vendida() + cantidadAVender);
                    cantidadAVender = 0;
                } else {
                    System.out.println ("2");
                    // Si la cantidad a vender es mayor que lo disponible, vender todo lo disponible en el historial
                    historialCosto.setCantidad_vendida(historialCosto.getCantidad_vendida() + cantidadDisponible);
                    cantidadAVender -= cantidadDisponible;
                }

                // Si se ha vendido todo del historial actual, marcarlo como INHABILITADO
                if (historialCosto.getCantidad() == historialCosto.getCantidad_vendida()) {
                    System.out.println ("inhabilitacion");
                    historialCosto.setEstado(HistorialCosto.Estado.INHABILITADO);
                }
            }

            // Si la cantidad a vender no ha sido completamente procesada, buscar el siguiente historial usando findSiguienteByFecha
            if (cantidadAVender > 0) {
                HistorialCosto siguienteHistorial = historialService.findSiguiente(historialCosto);
                // Marcar el historial actual como inhabilitado si se pasa al siguiente historial
                if (historialCosto.getCantidad() == historialCosto.getCantidad_vendida()) {
                    historialCosto.setEstado(HistorialCosto.Estado.INHABILITADO);
                }

                historialService.save (historialCosto);// Continuar con el siguiente historial
                venderRecursivo(producto, cantidadAVender, siguienteHistorial);

                return; // Si se pasó al siguiente historial, terminar la función para evitar procesar más historiales en la misma llamada
            }else{
                historialCosto.setEstado (HistorialCosto.Estado.INICIAL);
            }
        }

        if (cantidadAVender > 0) {
            // Si al final aún quedan productos por vender, lanzar excepción de stock insuficiente
            throw new InsufficientStockException("Verifique stock del producto: " + producto.getCodigobarras());
        }

        // Guardar los cambios en el historial de costos
        historialService.save(historialCosto);
    }

    @Override
    public void actualizarCategorias ( List<ProductoDTO> productosSeleccionados, String string ){
            Categoria categoria = categoriaService.getCategoria (string);

            productosSeleccionados.forEach(p -> {
                Producto productoActual = productoRepository.findById (p.getCodigobarras()).orElse(null);
                if(productoActual==null) { throw  new RuntimeException ("Producto no encontrado, código: " + p.getCodigobarras()); }

                Producto producto = new Producto (p.getCodigobarras(),
                        p.getNombre(),
                        categoria,
                        p.getCosto_compra (),
                        p.getPrecio_venta (),
                        p.getStock ()
                );


                commandInvoker.executeCommand (new UpdateProductCommand (this,historialService,productoActual,producto));
            });

    }

    @Override
    public void updatePrecio ( List<ProductoDTO> productosDTO, double porcentaje ){

        productosDTO.forEach(p -> {
            Producto productoActual = productoRepository.findById (p.getCodigobarras()).orElse(null);

            if(productoActual == null) throw  new RuntimeException ("Producto no encontrado");

            if(porcentaje >= 15000) {
                throw new RuntimeException ("Porcentaje excesivo, para continuar, elija otro porcentaje");
            }

            Producto productoNuevo = new Producto (productoActual);
            double precioNuevo= Math.round((p.getPrecio_venta() + p.getPrecio_venta() * porcentaje / 100d) * 100.0d) / 100.0d;
            if(precioNuevo > 999999999999d){
                throw new RuntimeException ("Precio excesivo en item: " + productoActual.getCodigobarras() + ", para continuar con el proceso elija otros productos o cambie manualmente el precio del item.");
            }
            productoNuevo.setPrecio_venta(precioNuevo);

            commandInvoker.executeCommand (new UpdateProductCommand (this,historialService,productoActual,productoNuevo));
            });
    }

    @Override
    public boolean cambios (){
        return this.commandInvoker.getCount () != 0;
    }

    @Override
    public void reembolsarProducto(Producto producto, int cantidadReembolsar) {
        reembolsarRecursivo(producto, cantidadReembolsar, historialService.findHistorialInicial(producto));
    }

    private void reembolsarRecursivo(Producto producto, int cantidadReembolsar, HistorialCosto historialCosto) {
        producto.setStock(producto.getStock() + cantidadReembolsar);

        historialCosto.setCantidad_vendida(historialCosto.getCantidad_vendida() - cantidadReembolsar);

        if (historialCosto.getCantidad_vendida() < 0) {
            HistorialCosto historialCostoAnterior = historialService.findAnterior(historialCosto);

            if (historialCostoAnterior == null) {
                throw new RuntimeException("Error al reembolsar el producto");
            }

            historialCostoAnterior.setCantidad_vendida(historialCostoAnterior.getCantidad_vendida() + historialCosto.getCantidad_vendida());
            historialCostoAnterior.setEstado(HistorialCosto.Estado.INICIAL);
            historialService.save(historialCostoAnterior);

            historialCosto.setCantidad_vendida(0);
            historialCosto.setEstado(HistorialCosto.Estado.SIGUIENTE);

            reembolsarRecursivo(producto, Math.abs(historialCosto.getCantidad_vendida()), historialCostoAnterior);
        }

        if (historialCosto.getEstado() == HistorialCosto.Estado.INHABILITADO) {
            historialCosto.setEstado(HistorialCosto.Estado.INICIAL);
        }

        productoRepository.save(producto);
        historialService.save(historialCosto);
    }
    @Override
    public void setCategoriaNull ( long id ){
        productoRepository.setCategoriaNull (id);
    }

}
