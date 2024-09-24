package com.libcentro.demo.services;

import java.util.List;

import com.libcentro.demo.exceptions.InsufficientStockException;
import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.repository.IhistorialcostosRepository;
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
    private IhistorialcostosRepository historialPreciosRepo;

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

        // Guardar el producto (esto también guardará el historial debido al Cascade)
        productoRepo.save(producto);

        return producto;
    }

    @Override
    public void deleteProducto(Producto x) {
        productoRepo.delete(x);
    }

    @Override
    public void updateProducto(Producto productoActualizado) {
        // Buscar el producto existente por su código de barras
        Producto productoExistente = productoRepo.findById(productoActualizado.getCodigo_barras())
                .orElseThrow(() -> new ObjectNotFoundException(Producto.class, productoActualizado.getCodigo_barras()));

        // Actualizar los atributos excepto el código de barras
        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setCategoria(productoActualizado.getCategoria());
        productoExistente.setCosto_compra(productoActualizado.getCosto_compra());
        productoExistente.setPrecio_venta(productoActualizado.getPrecio_venta());
        productoExistente.setCosto_inicial(productoActualizado.getCosto_inicial());
        productoExistente.setStock(productoActualizado.getStock());

        productoRepo.save(productoExistente);
    }

    @Override
    public void venderProducto(Producto producto, int cantidad){
        Producto productoExistente = productoRepo.findById(producto.getCodigo_barras())
                .orElseThrow(() -> new ObjectNotFoundException(Producto.class, producto.getCodigo_barras()));

        productoExistente.setStock(producto.getStock()-cantidad);
        productoRepo.save(productoExistente);
    }

    @Override
    public Producto getProducto(String codigo_barras) {
       Producto producto = productoRepo.findByCodigoBarrasConHistorial(codigo_barras);

       if (producto == null) {
           throw new ObjectNotFoundException(Producto.class,"El producto con código: " + codigo_barras + " no existe");
       }
       else {
           return producto;
       }
    }



    @Override
    public Producto getProducto(String codigo_barras, int cantidad) {
        Producto producto = productoRepo.findById(codigo_barras).orElse(null);
        if (producto == null) {
            throw new ObjectNotFoundException(Producto.class,"El producto con código: " + codigo_barras + " no existe");
        }
        if (producto.getStock()<cantidad) {
            throw new InsufficientStockException("el producto " + producto.getNombre() + "cod: " + codigo_barras + " no tiene stock suficiente.");
        }
        else {
            return producto;
        }
    }


}
