package com.libcentro.demo.services;

import java.util.List;

import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.repository.IhistorialpreciosRepository;
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
    private IhistorialpreciosRepository historialPreciosRepo;

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
        HistorialPrecio nuevoHistorial = new HistorialPrecio(producto, producto.getCosto_compra(), producto.getStock());
        producto.agregarHistorial(nuevoHistorial);

        // Guardar el producto (esto también guardará el historial debido al Cascade)
        productoRepo.save(producto);

        return producto;
    }

    @Override
    public void deleteProducto(Producto x) {
        productoRepo.delete(x);
    }

    @Override
    public void updateProducto(Producto x) {
        productoRepo.save(x);
    }

    @Override
    public Producto getProducto(String codigo_barras) {
       Producto producto = productoRepo.findById(codigo_barras).orElse(null);
       if (producto == null) {
           throw new ObjectNotFoundException(Producto.class, codigo_barras);
       }
       else {
           return producto;
       }
    }


}
