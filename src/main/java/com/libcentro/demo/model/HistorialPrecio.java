package com.libcentro.demo.model;


import com.libcentro.demo.model.dto.ProductoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="historial_precios")
public class HistorialPrecio  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "codigobarras")
    private Producto producto;
    private double precioVenta;
    private LocalDateTime fecha;


    public HistorialPrecio ( Producto nuevoProducto, double precioVenta ){
        this.producto = nuevoProducto;
        this.precioVenta = precioVenta;
    }
}
