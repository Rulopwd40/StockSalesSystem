package com.libcentro.demo.model;


import com.libcentro.demo.model.dto.ProductoDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


@Data
@Entity
@Table(name="historial_precios")
public class HistorialPrecio  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "codigobarras")
    Producto producto;

    @Column(name="precio_venta")
    private double precio_venta;

    @Column(name="fecha")
    private String fecha;


}
