package com.libcentro.demo.utils.command;

import com.libcentro.demo.model.Producto;
import com.libcentro.demo.services.interfaces.IproductoService;

public class AddProductCommand implements Command {

    private final Producto producto;
    private final IproductoService _productoService;

    public AddProductCommand(IproductoService productoService,Producto producto) {
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
