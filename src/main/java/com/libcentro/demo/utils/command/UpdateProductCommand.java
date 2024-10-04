package com.libcentro.demo.utils.command;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.services.interfaces.IhistorialCostosService;
import com.libcentro.demo.services.interfaces.IhistorialPreciosService;
import com.libcentro.demo.services.interfaces.IproductoService;

public class UpdateProductCommand implements Command {

    private final Producto viejoProducto;
    private final Producto nuevoProducto;
    private final IproductoService _productoService;
    private final IhistorialCostosService _historialcostosService;
    private final IhistorialPreciosService _historialpreciosService;

    public UpdateProductCommand(IproductoService productoService, IhistorialCostosService costoRepo, IhistorialPreciosService preciosRepo, Producto viejoProducto, Producto nuevoProducto) {
        this.viejoProducto = viejoProducto;
        this.nuevoProducto = nuevoProducto;
        _productoService = productoService;
        _historialpreciosService = preciosRepo;
        _historialcostosService = costoRepo;
    }


    @Override
    public void execute() {

        int cantidad = nuevoProducto.getStock()-viejoProducto.getStock();

        boolean costoCambiado = viejoProducto.getCosto_compra() != nuevoProducto.getCosto_compra();
        boolean cantidadCambiada = cantidad != 0;
        boolean precioVentaCambiado = viejoProducto.getPrecio_venta() != nuevoProducto.getPrecio_venta();

        // Actualizar el costo de compra y crear un nuevo historial
        if (costoCambiado && cantidad > 0) {
            HistorialCosto nuevoHistorial = new HistorialCosto(nuevoProducto,nuevoProducto.getCosto_compra(),cantidad);
            _historialcostosService.save(nuevoHistorial);
        } else if (cantidadCambiada && !costoCambiado) {
            // Actualizar cantidad en el historial actual
            HistorialCosto historialExistente = _historialcostosService.findFirstByProductoOrderByIdDesc(nuevoProducto);
            historialExistente.setCantidad(historialExistente.getCantidad() + cantidad);
            _historialcostosService.save(historialExistente);
        } else if(costoCambiado && !cantidadCambiada){
            throw new RuntimeException("No se puede cambiar el costo sin una variación en la cantidad");
        }

        // Actualizar precio de venta
        if (precioVentaCambiado) {
            HistorialPrecio nuevoHistorialPrecio = new HistorialPrecio(nuevoProducto,nuevoProducto.getPrecio_venta());
            _historialpreciosService.save(nuevoHistorialPrecio);
        }
        _productoService.saveProducto(nuevoProducto);

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
            HistorialCosto ultimoHistorial = _historialcostosService.findFirstByProductoOrderByIdDesc(nuevoProducto);
            _historialcostosService.delete(ultimoHistorial);
        } else if (cantidadCambiada && !costoCambiado) {
            // Restar la cantidad agregada previamente al historial actual
            HistorialCosto historialExistente = _historialcostosService.findFirstByProductoOrderByIdDesc(nuevoProducto);
            historialExistente.setCantidad(historialExistente.getCantidad() - cantidad);
            _historialcostosService.save(historialExistente);
        }

        // Revertir el precio de venta
        if (precioVentaCambiado) {
            // Encontrar y eliminar el último historial de precios
            HistorialPrecio ultimoHistorialPrecio = _historialpreciosService.findFirstByProductoOrderByIdDesc(nuevoProducto);
            _historialpreciosService.delete(ultimoHistorialPrecio);
        }

        // Finalmente, revertir el producto a su estado anterior
        nuevoProducto.setCosto_compra(viejoProducto.getCosto_compra());
        nuevoProducto.setPrecio_venta(viejoProducto.getPrecio_venta());
        nuevoProducto.setStock(viejoProducto.getStock());
        _productoService.saveProducto(viejoProducto);
    }
}
