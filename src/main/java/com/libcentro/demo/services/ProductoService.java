package com.libcentro.demo.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.libcentro.demo.exceptions.InsufficientStockException;
import com.libcentro.demo.model.Categoria;
import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.repository.IhistorialcostosRepository;
import com.libcentro.demo.repository.IhistorialpreciosRepository;
import com.libcentro.demo.utils.command.AddProductCommand;
import com.libcentro.demo.utils.command.CommandInvoker;
import dto.UpdateProductoPorcentajeDTO;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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
        // Verificar si el producto ya existe en la base de datos
        if (x.getCodigo_barras() != null) {
            Producto existingProducto = productoRepo.findById(x.getCodigo_barras()).orElse(null);
            if (existingProducto != null) {
                throw new ObjectOptimisticLockingFailureException(Producto.class, x.getCodigo_barras());
            }
        }
        productoRepo.save(x);
    }
    @Override
    public Producto crearProducto(Producto producto) {

        // Crear el historial de precios y agregarlo al producto
        HistorialCosto nuevoHistorial = new HistorialCosto(producto, producto.getCosto_compra(), producto.getStock());
        producto.agregarHistorial(nuevoHistorial);
        HistorialPrecio nuevoHistorialPrecio = new HistorialPrecio(producto,producto.getPrecio_venta());
        producto.agregarHistorial(nuevoHistorialPrecio);

        commandInvoker.executeCommand(new AddProductCommand(this, producto));

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
    public void venderProducto(Producto producto, int cantidad){
        Producto productoExistente = productoRepo.findById(producto.getCodigo_barras())
                .orElseThrow(() -> new ObjectNotFoundException(Producto.class, producto.getCodigo_barras()));

        productoExistente.setStock(producto.getStock()-cantidad);
        productoRepo.save(productoExistente);
    }

    public HistorialPrecio anadirHistorialPrecio(Producto producto){
        HistorialPrecio historialPrecio= new HistorialPrecio(producto,producto.getPrecio_venta());
        historialPreciosRepo.save(historialPrecio);
        return historialPrecio;
    }
    ;
}
