package com.libcentro.demo.model.dto;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.Producto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistorialCostoDTO {

    private Long id;
    private String codigobarras;
    private double costo_compra;
    private int cantidad;
    HistorialCosto.Estado estado;
    private LocalDateTime fecha;
    private int cantidad_vendida;

    public HistorialCostoDTO(HistorialCosto costo) {
        this.id = costo.getId();
        this.codigobarras = costo.getProducto ().getCodigobarras ();
        this.costo_compra = costo.getCosto_compra();
        this.cantidad = costo.getCantidad();
        this.estado = costo.getEstado();
        this.fecha = costo.getFecha();
        this.cantidad_vendida = costo.getCantidad_vendida();

    }
}
