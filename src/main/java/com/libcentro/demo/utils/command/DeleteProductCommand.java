package com.libcentro.demo.utils.command;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.services.interfaces.IhistorialService;
import com.libcentro.demo.services.interfaces.IproductoService;

import java.util.HashSet;
import java.util.Set;

public class DeleteProductCommand implements Command {

    private final IproductoService productoService;
    private final Producto productoOriginal;
    private final IhistorialService historialService;

    private final Set<HistorialPrecio> historialPrecioClonado;
    private final Set<HistorialCosto> historialCostoClonado;

    public DeleteProductCommand( IproductoService productoService, Producto producto, IhistorialService historialService ) {
        this.productoService = productoService;
        this.productoOriginal = producto;

        // Clona los historiales
        this.historialPrecioClonado = new HashSet<>();
        for (HistorialPrecio precio : historialService.findAllPreciosByProducto(producto)) {
            historialPrecioClonado.add(cloneHistorialPrecio(precio));
        }

        this.historialCostoClonado = new HashSet<>();
        for (HistorialCosto costo : historialService.findAllCostosByProducto(producto)) {
            historialCostoClonado.add(cloneHistorialCosto(costo));
        }

        this.historialService = historialService;

    }

    @Override
    public void execute() {
        productoService.deleteProductoByCodigo(productoOriginal.getCodigoBarras ());
    }

    @Override
    public void undo() {
        // Restaurar el producto
        Producto producto = new Producto(productoOriginal);
        productoService.saveProducto(producto);
        producto = productoService.getProducto(productoOriginal.getCodigoBarras ());

        // Restaurar los historiales
        for (HistorialPrecio precio : historialPrecioClonado) {
            precio.setProducto(producto); // Asociar el producto restaurado
            historialService.save(precio);
        }

        for (HistorialCosto costo : historialCostoClonado) {
            costo.setProducto(producto); // Asociar el producto restaurado
            historialService.save(costo);
        }
    }

    private HistorialPrecio cloneHistorialPrecio(HistorialPrecio original) {
        HistorialPrecio clone = new HistorialPrecio();
        clone.setPrecio_venta(original.getPrecio_venta());
        clone.setFecha(original.getFecha());
        // Clonar otras propiedades según sea necesario
        return clone;
    }

    private HistorialCosto cloneHistorialCosto(HistorialCosto original) {
        HistorialCosto clone = new HistorialCosto();
        clone.setCosto_compra(original.getCosto_compra());
        clone.setCantidad(original.getCantidad());
        clone.setEstado(original.getEstado ());
        clone.setFecha(original.getFecha());
        // Clonar otras propiedades según sea necesario
        return clone;
    }
}