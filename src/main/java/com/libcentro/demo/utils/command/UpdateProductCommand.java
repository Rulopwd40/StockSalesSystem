package com.libcentro.demo.utils.command;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.services.interfaces.IhistorialService;
import com.libcentro.demo.services.interfaces.IproductoService;
import jakarta.transaction.Transactional;

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
    @Transactional
    public void execute() {

        int cantidad = nuevoProducto.getStock()-viejoProducto.getStock();

        boolean costoCambiado = viejoProducto.getCosto_compra() != nuevoProducto.getCosto_compra();
        boolean cantidadCambiada = cantidad != 0;
        boolean precioVentaCambiado = viejoProducto.getPrecio_venta() != nuevoProducto.getPrecio_venta();

        if (costoCambiado && cantidadCambiada) {
            if(Math.abs (nuevoProducto.getCosto_compra ()-viejoProducto.getCosto_compra()) > viejoProducto.getCosto_compra () * 0.0025d) {
                HistorialCosto historialCosto = historialService.crearHistorialCosto (nuevoProducto, nuevoProducto.getCosto_compra (), cantidad, HistorialCosto.Estado.SIGUIENTE);
                historialService.save (historialCosto);
            }
        } else if ( cantidadCambiada ) {
            HistorialCosto historialCosto = historialService.findLastHistorialCosto (viejoProducto);
            historialCosto.setCantidad(historialCosto.getCantidad() + cantidad);
            historialService.save(historialCosto);
        } else if( costoCambiado ){
            HistorialCosto historialCosto = historialService.findLastHistorialCosto (viejoProducto);
            historialCosto.setCosto_compra (nuevoProducto.getCosto_compra());
        }
        if (precioVentaCambiado) {
            if(Math.abs (nuevoProducto.getPrecio_venta ()-viejoProducto.getPrecio_venta()) > viejoProducto.getPrecio_venta() * 0.0025d) {
                HistorialPrecio nuevoHistorialPrecio = historialService.crearHistorialPrecio (nuevoProducto, nuevoProducto.getPrecio_venta ());
                historialService.save (nuevoHistorialPrecio);
            }
        }
        productoService.saveProducto (nuevoProducto);

    }

    @Override
    public void undo() {

        int cantidad = nuevoProducto.getStock() - viejoProducto.getStock();

        boolean costoCambiado = viejoProducto.getCosto_compra() != nuevoProducto.getCosto_compra();
        boolean cantidadCambiada = cantidad != 0;
        boolean precioVentaCambiado = viejoProducto.getPrecio_venta() != nuevoProducto.getPrecio_venta();

        if (costoCambiado && cantidadCambiada) {
            if (Math.abs(nuevoProducto.getCosto_compra() - viejoProducto.getCosto_compra()) > viejoProducto.getCosto_compra() * 0.0025d) {
                HistorialCosto ultimoHistorial = historialService.findLastHistorialCosto(nuevoProducto);
                historialService.delete(ultimoHistorial);
            }
        } else if (cantidadCambiada) {
            HistorialCosto historialCosto = historialService.findLastHistorialCosto(nuevoProducto);
            historialCosto.setCantidad(historialCosto.getCantidad() - cantidad);
            historialService.save(historialCosto);
        } else if (costoCambiado) {
            HistorialCosto historialCosto = historialService.findLastHistorialCosto(nuevoProducto);
            historialCosto.setCosto_compra(viejoProducto.getCosto_compra());
        }

        if (precioVentaCambiado) {
            if (Math.abs(nuevoProducto.getPrecio_venta() - viejoProducto.getPrecio_venta()) > viejoProducto.getPrecio_venta() * 0.0025d) {
                HistorialPrecio ultimoHistorialPrecio = historialService.findLastHistorialPrecio(nuevoProducto);
                historialService.delete(ultimoHistorialPrecio);
            }
        }

        nuevoProducto.setCosto_compra(viejoProducto.getCosto_compra());
        nuevoProducto.setPrecio_venta(viejoProducto.getPrecio_venta());
        nuevoProducto.setStock(viejoProducto.getStock());
        productoService.saveProducto(viejoProducto);
    }
}
