package com.libcentro.demo.services;

import java.util.List;

import com.libcentro.demo.exceptions.InsufficientStockException;
import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.repository.IhistorialcostosRepository;
import com.libcentro.demo.repository.IhistorialpreciosRepository;
import com.libcentro.demo.utils.command.AddProductCommand;
import com.libcentro.demo.utils.command.CommandInvoker;
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
    private IhistorialcostosRepository historialCostosRepo;
    @Autowired
    private IhistorialpreciosRepository historialPreciosRepo;

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
        commandInvoker.executeCommand(new AddProductCommand(this, producto,historialPreciosRepo,historialCostosRepo));


        return producto;
    }

    @Override
    public void deleteProducto(Producto x) {
        productoRepo.delete(x);
    }

    @Override
    public void updateProducto(Producto productoActualizado) {

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
        historialPreciosRepo.save(historialPrecio);
        return historialPrecio;
    }




}
