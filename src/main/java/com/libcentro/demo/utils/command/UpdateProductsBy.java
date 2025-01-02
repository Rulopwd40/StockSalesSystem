package com.libcentro.demo.utils.command;

import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.services.interfaces.IhistorialService;
import com.libcentro.demo.services.interfaces.IproductoService;

import java.util.List;

public class UpdateProductsBy implements Command {

    private final List<Producto> nuevosProductos;
    private final List<Producto> viejosProductos;
    private final IproductoService productoService;
    private final IhistorialService historialService;

    public UpdateProductsBy(IproductoService productoService, List<Producto> nuevosProductos, List<Producto> viejosProductos, IhistorialService historialService) {
        this.nuevosProductos = nuevosProductos;
        this.viejosProductos = viejosProductos;
        this.productoService = productoService;
        this.historialService = historialService;
    }


    @Override
    public void execute() {
        nuevosProductos.forEach(producto -> {
            HistorialPrecio historialPrecio = new HistorialPrecio(producto, producto.getPrecio_venta());
            historialService.save(historialPrecio);
            productoService.saveProducto (producto);
        });
    }

    @Override
    public void undo() {
        for (int i = 0; i < nuevosProductos.size(); i++) {
            Producto nuevoProducto = nuevosProductos.get(i);
            Producto viejoProducto = viejosProductos.get(i);

            // Buscar el historial creado más recientemente para este producto y eliminarlo
            HistorialPrecio historialReciente = historialService.findLastHistorialPrecio(nuevoProducto);
            if (historialReciente != null) {
                historialService.delete(historialReciente); // Eliminar el historial reciente
            }

            // Crear un nuevo historial de precios para revertir al precio original
            HistorialPrecio historialPrecio = new HistorialPrecio(viejoProducto, viejoProducto.getPrecio_venta());
            historialService.save(historialPrecio);

            // Revertir los cambios del producto al viejo producto
            nuevoProducto.setPrecio_venta(viejoProducto.getPrecio_venta());
            productoService.saveProducto (nuevoProducto);
        }
    }

}
