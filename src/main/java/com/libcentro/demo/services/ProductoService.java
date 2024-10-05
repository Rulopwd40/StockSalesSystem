package com.libcentro.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import com.libcentro.demo.exceptions.InsufficientStockException;
import com.libcentro.demo.model.Categoria;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.repository.IhistorialcostosRepository;
import com.libcentro.demo.repository.IhistorialpreciosRepository;
import com.libcentro.demo.services.interfaces.IhistorialCostosService;
import com.libcentro.demo.services.interfaces.IhistorialPreciosService;
import com.libcentro.demo.utils.command.*;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libcentro.demo.model.Producto;
import com.libcentro.demo.repository.IproductoRepository;
import com.libcentro.demo.services.interfaces.IproductoService;

@Service
public class ProductoService implements IproductoService {
    @Autowired
    private IproductoRepository productoRepo;
    @Autowired
    private IhistorialCostosService historialCostosService;
    @Autowired
    private IhistorialPreciosService historialPreciosService;




    private final CommandInvoker commandInvoker = new CommandInvoker();

    @Override
    public List<Producto> getAll() {
        return productoRepo.findAll();
    }

    @Override
    public void saveProducto(Producto x) {
        productoRepo.save(x);
    }
    @Override
    @Transactional
    public Producto crearProducto(Producto producto) {

        Producto existingProducto = productoRepo.findById(producto.getCodigo_barras()).orElse(null);

        if (existingProducto != null) {
            throw new RuntimeException("El producto con codigo: " + producto.getCodigo_barras() + " ya existe.");
        }
        commandInvoker.executeCommand(new AddProductCommand(this, producto, historialPreciosService, historialCostosService));


        return producto;
    }

    @Override
    public void deleteProducto(Producto x) {
        productoRepo.delete(x);
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
    public void updateProducto(Producto productoActualizado) {
        Producto productoActual= productoRepo.findById(productoActualizado.getCodigo_barras()).orElse(null);

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
