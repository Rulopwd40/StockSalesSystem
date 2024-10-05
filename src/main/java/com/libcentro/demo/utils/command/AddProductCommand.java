package com.libcentro.demo.utils.command;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.repository.IhistorialcostosRepository;
import com.libcentro.demo.repository.IhistorialpreciosRepository;
import com.libcentro.demo.services.interfaces.IhistorialCostosService;
import com.libcentro.demo.services.interfaces.IhistorialPreciosService;
import com.libcentro.demo.services.interfaces.IproductoService;

public class AddProductCommand implements Command {

    private final Producto producto;
    private final IproductoService _productoService;
    private final IhistorialPreciosService _historialPrecio;
    private final IhistorialCostosService _historialCosto;
    private HistorialPrecio historialPrecio;
    private HistorialCosto historialCosto;


    public AddProductCommand(IproductoService productoService, Producto producto, IhistorialPreciosService historialPrecio, IhistorialCostosService historialCosto) {
        _productoService = productoService;
        this.producto = producto;
        _historialPrecio = historialPrecio;
        _historialCosto = historialCosto;
    }


    @Override
    public void execute() {
        _productoService.saveProducto(producto);
        historialPrecio = new HistorialPrecio(producto, producto.getPrecio_venta());
        historialCosto = new HistorialCosto(producto, producto.getCosto_compra(), producto.getStock());
        _historialPrecio.save(historialPrecio);
        _historialCosto.save(historialCosto);
    }

    @Override
    public void undo() {
        _productoService.deleteProducto(producto);
        _historialPrecio.delete(historialPrecio);
        _historialCosto.delete(historialCosto);
    }
}
