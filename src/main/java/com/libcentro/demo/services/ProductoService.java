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
    public void updateProducto(Producto productoActualizado,HistorialPrecio historialPrecio,HistorialCosto historialCosto) {
        // Buscar el producto existente por su código de barras
        Producto productoExistente = productoRepo.findByCodigoBarras(productoActualizado.getCodigo_barras());

        // Actualizar los atributos excepto el código de barras
        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setCategoria(productoActualizado.getCategoria());
        productoExistente.setCosto_compra(productoActualizado.getCosto_compra());
        productoExistente.setPrecio_venta(productoActualizado.getPrecio_venta());
        productoExistente.setCosto_inicial(productoActualizado.getCosto_inicial());
        productoExistente.setStock(productoActualizado.getStock());

        if(historialPrecio != null) {historialPreciosRepo.save(historialPrecio);}
        if(historialCosto != null) {historialCostosRepo.save(historialCosto);}

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
       Producto producto = productoRepo.findByCodigoBarrasWithHistorial(codigo_barras);

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

    @Override
    public Producto getProductoByName(String nombre){
        Producto producto = productoRepo.findByNombre(nombre);
        if (producto == null) {
            throw new ObjectNotFoundException(Producto.class,"El producto con código: " + nombre + " no existe");
        }
        else return producto;
    }


    @Override
    @Transactional
    public UpdateProductoPorcentajeDTO updatePrecioPorCategoria(Categoria categoria, BigDecimal porcentaje) {

        int cant=0;
        List<Producto> productos = (List<Producto>) getProductoPorCategoria(categoria);
        Set<HistorialPrecio> precios = new HashSet<>();
        for (Producto producto : productos) {
            BigDecimal precioActual = new BigDecimal(producto.getPrecio_venta());

            BigDecimal incremento = precioActual.multiply(porcentaje).divide(new BigDecimal(100));
            BigDecimal nuevoPrecio = precioActual.add(incremento);

            nuevoPrecio = nuevoPrecio.setScale(2, RoundingMode.HALF_UP);

            // Convertimos el nuevo precio a int
            float nuevoPrecioFloat = nuevoPrecio.floatValue();

            // Establecemos el nuevo precio (convertido a int) en el producto
            producto.setPrecio_venta(nuevoPrecioFloat);

            // Registramos el historial del precio
            precios.add(anadirHistorialPrecio(producto));

            cant++;
        }

        return new UpdateProductoPorcentajeDTO(productos,cant,precios,porcentaje);
    }


    @Override
    @Transactional
    public UpdateProductoPorcentajeDTO updatePrecioGeneral(BigDecimal porcentaje) {
        int cant = 0;
        List<Producto> productos = getAll();
        Set<HistorialPrecio> precios = new HashSet<>();
        for (Producto producto : productos) {
            BigDecimal precioActual = new BigDecimal(producto.getPrecio_venta());

            BigDecimal incremento = precioActual.multiply(porcentaje).divide(new BigDecimal(100));
            BigDecimal nuevoPrecio = precioActual.add(incremento);

            nuevoPrecio = nuevoPrecio.setScale(2, RoundingMode.HALF_UP);

            // Convertimos el nuevo precio a int
            float nuevoPrecioFloat = nuevoPrecio.floatValue();

            // Establecemos el nuevo precio (convertido a int) en el producto
            producto.setPrecio_venta(nuevoPrecioFloat);

            // Registramos el historial del precio
            precios.add(anadirHistorialPrecio(producto));

            cant++;
        }

        // Guardamos todos los productos actualizados en la base de datos
        productoRepo.saveAll(productos);

        return new UpdateProductoPorcentajeDTO(productos,cant,precios,porcentaje.divide(new BigDecimal(100)));

    }


    @Override
    public Set<Producto> getProductoPorCategoria(Categoria categoria){
        return productoRepo.findByCategoria(categoria);
    }

    public HistorialPrecio anadirHistorialPrecio(Producto producto){
        HistorialPrecio historialPrecio= new HistorialPrecio(producto,producto.getPrecio_venta());
        historialPreciosRepo.save(historialPrecio);
        return historialPrecio;
    }
    ;
}
