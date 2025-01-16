package com.libcentro.demo.model.dto;

import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistorialPrecioDTO {

    private Long id;
    private String codigobarras;
    private double precioVenta;
    private LocalDateTime fecha;

    public HistorialPrecioDTO( HistorialPrecio historialPrecio) {
        this.id = historialPrecio.getId();
        this.codigobarras = historialPrecio.getProducto ().getCodigobarras ();
        this.precioVenta = historialPrecio.getPrecioVenta();
        this.fecha = historialPrecio.getFecha();
    }


}
