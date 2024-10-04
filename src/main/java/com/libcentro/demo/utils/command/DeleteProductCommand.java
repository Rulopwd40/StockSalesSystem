package com.libcentro.demo.utils.command;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.services.interfaces.IhistorialCostosService;
import com.libcentro.demo.services.interfaces.IhistorialPreciosService;
import com.libcentro.demo.services.interfaces.IproductoService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeleteProductCommand implements Command {

    private final IproductoService productoService;
    private final Producto productoOriginal;
    private final IhistorialPreciosService historialPreciosService;
    private final IhistorialCostosService historialCostosService;
    private final Set<HistorialPrecio> historialPrecio;
    private final Set<HistorialCosto> historialCosto;

    public DeleteProductCommand(IproductoService productoService, Producto producto, IhistorialPreciosService historialPreciosService, IhistorialCostosService historialCostosService) {
        this.productoService = productoService;
        // Clona el producto junto con su historial para poder restaurarlo
        this.productoOriginal = producto;
        this.historialPrecio = historialPreciosService.findAllByProducto(producto);
        this.historialCosto = historialCostosService.findAllByProducto(producto);
        this.historialPreciosService = historialPreciosService;
        this.historialCostosService = historialCostosService;
    }

    @Override
    public void execute() {
        productoService.deleteProductoByCodigo(productoOriginal.getCodigo_barras());
    }

    @Override
    public void undo() {
        productoService.saveProducto(productoOriginal);
        Producto producto = productoService.getProducto(productoOriginal.getCodigo_barras());

        historialPrecio.forEach(historialPrecio1 -> historialPrecio1.setProducto(producto));
        historialCosto.forEach(historialCosto1 -> historialCosto1.setProducto(producto));

        historialPreciosService.saveAll(historialPrecio);
        historialCostosService.saveAll(historialCosto);
    }

}
