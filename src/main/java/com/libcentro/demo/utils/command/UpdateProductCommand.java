package com.libcentro.demo.utils.command;

import com.libcentro.demo.model.Producto;
import com.libcentro.demo.services.interfaces.IproductoService;

public class UpdateProductCommand implements Command {

    private final Producto viejoProducto;
    private final Producto nuevoProducto;
    private final IproductoService _productoService;
    public UpdateProductCommand(IproductoService productoService,Producto viejoProducto, Producto nuevoProducto) {
        this.viejoProducto = viejoProducto;
        this.nuevoProducto = nuevoProducto;
        _productoService = productoService;
    }


    @Override
    public void execute() {
        _productoService.updateProducto(nuevoProducto);
    }

    @Override
    public void undo() {
        _productoService.updateProducto(viejoProducto);
    }
}
