package com.libcentro.demo.utils.command;

import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.services.interfaces.IhistorialService;
import com.libcentro.demo.services.interfaces.IproductoService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            HistorialPrecio historialPrecio = historialService.crearHistorialPrecio (producto, producto.getPrecio_venta ());
            historialService.save(historialPrecio);
            productoService.saveProducto (producto);
        });
    }

    @Override
    public void undo() {
        Map<String, Producto> productosViejosMap = viejosProductos.stream()
                .collect(Collectors.toMap(Producto::getCodigobarras, producto -> producto));

        for (Producto nuevoProducto : nuevosProductos) {
            Producto viejoProducto = productosViejosMap.get(nuevoProducto.getCodigobarras());
            if (viejoProducto == null) {
                throw new RuntimeException("Producto no encontrado en los productos viejos");
            }

            HistorialPrecio historialReciente = historialService.findLastHistorialPrecio(nuevoProducto);
            if (historialReciente != null) {
                historialService.delete(historialReciente);
            }

            nuevoProducto.setPrecio_venta(viejoProducto.getPrecio_venta());
            productoService.saveProducto(nuevoProducto);
        }
    }
 }


