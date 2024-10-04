package com.libcentro.demo.utils.command;

import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.repository.IhistorialpreciosRepository;
import com.libcentro.demo.services.interfaces.IproductoService;

import java.util.List;

public class UpdateProductsBy implements Command {

    private final List<Producto> nuevosProductos;
    private final List<Producto> viejosProductos;
    private final IproductoService productoService;
    private final IhistorialpreciosRepository _historialpreciosRepository;

    public UpdateProductsBy(IproductoService productoService,List<Producto> nuevosProductos, List<Producto> viejosProductos, IhistorialpreciosRepository historialpreciosRepository) {
        this.nuevosProductos = nuevosProductos;
        this.viejosProductos = viejosProductos;
        this.productoService = productoService;
        _historialpreciosRepository = historialpreciosRepository;
    }


    @Override
    public void execute() {
        nuevosProductos.forEach(producto -> {
            HistorialPrecio historialPrecio = new HistorialPrecio(producto, producto.getPrecio_venta());
            _historialpreciosRepository.save(historialPrecio);
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
            HistorialPrecio historialReciente = _historialpreciosRepository.findFirstByProductoOrderByIdDesc(nuevoProducto);
            if (historialReciente != null) {
                _historialpreciosRepository.delete(historialReciente); // Eliminar el historial reciente
            }

            // Crear un nuevo historial de precios para revertir al precio original
            HistorialPrecio historialPrecio = new HistorialPrecio(viejoProducto, viejoProducto.getPrecio_venta());
            _historialpreciosRepository.save(historialPrecio);

            // Revertir los cambios del producto al viejo producto
            nuevoProducto.setPrecio_venta(viejoProducto.getPrecio_venta());
            productoService.saveProducto(nuevoProducto);
        }
    }

}
