package com.libcentro.demo.utils.command;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.services.interfaces.IhistorialService;
import com.libcentro.demo.services.interfaces.IproductoService;
import org.hibernate.Hibernate;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

        if (costoCambiado && cantidadCambiada) {
            System.out.println("Entro primer if con cantidad:" + cantidad);
            HistorialCosto historialCosto = historialService.crearHistorialCosto (nuevoProducto,nuevoProducto.getCosto_compra(),cantidad, HistorialCosto.Estado.SIGUIENTE);
            nuevoProducto.getHistorial_costos ().add(historialCosto);
        } else if (cantidadCambiada && !costoCambiado) {
            System.out.println("Entro 2do if: " + cantidad);
            Hibernate.initialize (viejoProducto.getHistorial_costos ());
            List<HistorialCosto> historialCostos = viejoProducto.getHistorial_costos();

            Optional<HistorialCosto> historialCosto = historialCostos.stream()
                    .max(Comparator.comparing(HistorialCosto::getFecha));

            historialCosto.ifPresent (historialExistente -> {
                historialExistente.setCantidad(historialExistente.getCantidad() + cantidad);
            });

        } else if(costoCambiado && !cantidadCambiada){
            throw new RuntimeException("No se puede cambiar el costo sin una variación en la cantidad");
        }

        // Actualizar precio de venta
        if (precioVentaCambiado) {
            System.out.println("Entro 3do if");
            HistorialPrecio nuevoHistorialPrecio = historialService.crearHistorialPrecio(nuevoProducto,nuevoProducto.getPrecio_venta());
            historialService.save(nuevoHistorialPrecio);
        }
        productoService.saveProducto(nuevoProducto);

    }

    @Override
    public void undo() {

        int cantidad = nuevoProducto.getStock() - viejoProducto.getStock();
        boolean costoCambiado = viejoProducto.getCosto_compra() != nuevoProducto.getCosto_compra();
        boolean cantidadCambiada = cantidad != 0;
        boolean precioVentaCambiado = viejoProducto.getPrecio_venta() != nuevoProducto.getPrecio_venta();


        if (costoCambiado && cantidad > 0) {

            HistorialCosto ultimoHistorial = historialService.findLastHistorialCosto(nuevoProducto);
            historialService.delete(ultimoHistorial);
        } else if (cantidadCambiada && !costoCambiado) {

            HistorialCosto historialExistente = historialService.findLastHistorialCosto(nuevoProducto);
            historialExistente.setCantidad(historialExistente.getCantidad() - cantidad);
            historialService.save(historialExistente);
        }


        if (precioVentaCambiado) {

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
