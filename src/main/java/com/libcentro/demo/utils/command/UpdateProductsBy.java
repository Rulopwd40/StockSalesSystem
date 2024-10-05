package com.libcentro.demo.utils.command;

import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.services.interfaces.IhistorialPreciosService;
import com.libcentro.demo.services.interfaces.IproductoService;

import java.util.List;

public class UpdateProductsBy implements Command {

    private final List<Producto> nuevosProductos;
    private final List<Producto> viejosProductos;
    private final IproductoService productoService;
    private final IhistorialPreciosService _historialpreciosService;

    public UpdateProductsBy(IproductoService productoService, List<Producto> nuevosProductos, List<Producto> viejosProductos, IhistorialPreciosService historialpreciosRepository) {
        this.nuevosProductos = nuevosProductos;
        this.viejosProductos = viejosProductos;
        this.productoService = productoService;
        _historialpreciosService = historialpreciosRepository;
    }


    @Override
    public void execute() {
        nuevosProductos.forEach(producto -> {
            HistorialPrecio historialPrecio = new HistorialPrecio(producto, producto.getPrecio_venta());
            _historialpreciosService.save(historialPrecio);
            productoService.saveProducto(producto);
        });
    }

    @Override
    public void undo() {
        // Revertir los productos a sus versiones originales
        for (int i = 0; i < nuevosProductos.size(); i++) {
            Producto nuevoProducto = nuevosProductos.get(i);
            Producto viejoProducto = viejosProductos.get(i);

            // Buscar el historial creado más recientemente para este producto y eliminarlo
            HistorialPrecio historialReciente = _historialpreciosService.findFirstByProductoOrderByIdDesc(nuevoProducto);
            if (historialReciente != null) {
                _historialpreciosService.delete(historialReciente); // Eliminar el historial reciente
            }

            // Crear un nuevo historial de precios para revertir al precio original
            HistorialPrecio historialPrecio = new HistorialPrecio(viejoProducto, viejoProducto.getPrecio_venta());
            _historialpreciosService.save(historialPrecio);

            // Revertir los cambios del producto al viejo producto
            nuevoProducto.setPrecio_venta(viejoProducto.getPrecio_venta());
            productoService.saveProducto(nuevoProducto);
        }
    }

}
