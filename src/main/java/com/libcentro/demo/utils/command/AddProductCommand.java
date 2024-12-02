package com.libcentro.demo.utils.command;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.services.interfaces.IproductoService;

public class AddProductCommand implements Command {

    private Producto producto;
    private final IproductoService productoService;


    public AddProductCommand(IproductoService productoService, Producto producto) {
        this.productoService = productoService;
        this.producto = producto;
    }


    @Override
    public void execute() {
       this.producto= productoService.saveProducto(producto);
    }

    @Override
    public void undo() {
        productoService.deleteProducto(producto);
    }
}
