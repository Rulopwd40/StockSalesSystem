package com.libcentro.demo.utils.command;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.services.interfaces.IhistorialService;
import com.libcentro.demo.services.interfaces.IproductoService;
import jakarta.persistence.EntityManager;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class DeleteProductCommand implements Command {


    private final IproductoService productoService;
    private final Producto productoOriginal;
    private final IhistorialService historialService;


    private final List<HistorialPrecio> historialPrecioClonado;
    private final List<HistorialCosto> historialCostoClonado;

    public DeleteProductCommand( IproductoService productoService, Producto producto, IhistorialService historialService ) {
        this.productoService = productoService;
        this.productoOriginal = producto;

        this.historialPrecioClonado = new ArrayList<> ();
        Hibernate.initialize (producto.getHistorial_precios ());
        for (HistorialPrecio precio : producto.getHistorial_precios ()) {
            historialPrecioClonado.add(cloneHistorialPrecio(precio));
        }
        producto.setHistorial_precios(Collections.emptyList ());

        this.historialCostoClonado = new ArrayList<> ();
        Hibernate.initialize (producto.getHistorial_costos ());
        for (HistorialCosto costo : producto.getHistorial_costos ()) {
            historialCostoClonado.add(cloneHistorialCosto(costo));
        }
        producto.setHistorial_costos(Collections.emptyList ());
        this.historialService = historialService;

    }

    @Override
    public void execute() {
        //productoService.deleteProductoByCodigo(productoOriginal.getCodigobarras ());
    }

    @Override
    public void undo() {
        /*
        // Restaurar el producto
        Producto producto = new Producto(productoOriginal);
        productoService.saveProducto(producto);
        producto = productoService.getProducto(productoOriginal.getCodigobarras ());

        // Restaurar los historiales
        for (HistorialPrecio precio : historialPrecioClonado) {
            precio.setProducto(producto); // Asociar el producto restaurado
            historialService.save(precio);
        }

        for (HistorialCosto costo : historialCostoClonado) {
            costo.setProducto(producto); // Asociar el producto restaurado
            historialService.save(costo);
        }*/
    }

    private HistorialPrecio cloneHistorialPrecio(HistorialPrecio original) {
        HistorialPrecio clone = new HistorialPrecio();
        clone.setPrecioVenta(original.getPrecioVenta());
        clone.setFecha(original.getFecha());

        return clone;
    }

    private HistorialCosto cloneHistorialCosto(HistorialCosto original) {
        HistorialCosto clone = new HistorialCosto();
        clone.setCosto_compra(original.getCosto_compra());
        clone.setCantidad(original.getCantidad());
        clone.setEstado(original.getEstado ());
        clone.setFecha(original.getFecha());

        return clone;
    }
}