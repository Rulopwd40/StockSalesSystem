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

    private HistorialPrecio historialPrecio;
    private HistorialCosto historialCosto;


    public AddProductCommand(IproductoService productoService, Producto producto) {
        _productoService = productoService;
        this.producto = producto;
    }


    @Override
    public void execute() {
        _productoService.saveProducto(producto);
    }

    @Override
    public void undo() {
        _productoService.deleteProducto(producto);
    }
}
