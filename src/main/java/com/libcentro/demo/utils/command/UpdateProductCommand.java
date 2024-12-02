package com.libcentro.demo.utils.command;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.services.interfaces.IhistorialService;
import com.libcentro.demo.services.interfaces.IproductoService;

public class UpdateProductCommand implements Command {

    private final Producto viejoProducto;
    private final Producto nuevoProducto;
    private final IproductoService productoService;
    private final IhistorialService historialService;
;

    public UpdateProductCommand( IproductoService productoService, IhistorialService historialService, Producto viejoProducto, Producto nuevoProducto) {
        this.viejoProducto = viejoProducto;
        this.nuevoProducto = nuevoProducto;
        this.productoService = productoService;
        this.historialService = historialService;
    }


    @Override
    public void execute() {

        int cantidad = nuevoProducto.getStock()-viejoProducto.getStock();

        boolean costoCambiado = viejoProducto.getCosto_compra() != nuevoProducto.getCosto_compra();
        boolean cantidadCambiada = cantidad != 0;
        boolean precioVentaCambiado = viejoProducto.getPrecio_venta() != nuevoProducto.getPrecio_venta();

        if (costoCambiado && cantidad > 0) {
            historialService.crearHistorialCosto (nuevoProducto,nuevoProducto.getCosto_compra(),cantidad, HistorialCosto.Estado.INICIAL);
        } else if (cantidadCambiada && !costoCambiado) {
            HistorialCosto historialExistente = historialService.findLastHistorialCosto(viejoProducto);

            historialExistente.setCantidad(historialExistente.getCantidad() + cantidad);
            historialService.save(historialExistente);
        } else if(costoCambiado && !cantidadCambiada){
            throw new RuntimeException("No se puede cambiar el costo sin una variación en la cantidad");
        }

        // Actualizar precio de venta
        if (precioVentaCambiado) {
            HistorialPrecio nuevoHistorialPrecio = new HistorialPrecio(nuevoProducto,nuevoProducto.getPrecio_venta());
            historialService.save(nuevoHistorialPrecio);
        }
        productoService.saveProducto(nuevoProducto);

    }

    @Override
    public void undo() {
        // Revertir la cantidad y el costo de compra
        int cantidad = nuevoProducto.getStock() - viejoProducto.getStock();
        boolean costoCambiado = viejoProducto.getCosto_compra() != nuevoProducto.getCosto_compra();
        boolean cantidadCambiada = cantidad != 0;
        boolean precioVentaCambiado = viejoProducto.getPrecio_venta() != nuevoProducto.getPrecio_venta();

        // Si el costo cambió y se creó un nuevo historial, eliminarlo
        if (costoCambiado && cantidad > 0) {
            // Encontrar el último historial de costos y eliminarlo
            HistorialCosto ultimoHistorial = historialService.findLastHistorialCosto(nuevoProducto);
            historialService.delete(ultimoHistorial);
        } else if (cantidadCambiada && !costoCambiado) {
            // Restar la cantidad agregada previamente al historial actual
            HistorialCosto historialExistente = historialService.findLastHistorialCosto(nuevoProducto);
            historialExistente.setCantidad(historialExistente.getCantidad() - cantidad);
            historialService.save(historialExistente);
        }

        // Revertir el precio de venta
        if (precioVentaCambiado) {
            // Encontrar y eliminar el último historial de precios
            HistorialPrecio ultimoHistorialPrecio = historialService.findLastHistorialPrecio(nuevoProducto);
            historialService.delete(ultimoHistorialPrecio);
        }

        // Finalmente, revertir el producto a su estado anterior
        nuevoProducto.setCosto_compra(viejoProducto.getCosto_compra());
        nuevoProducto.setPrecio_venta(viejoProducto.getPrecio_venta());
        nuevoProducto.setStock(viejoProducto.getStock());
        productoService.saveProducto(viejoProducto);
    }
}
