package com.libcentro.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


@Data
@Entity
@Table(name="historial_costos")
public class HistorialCosto {

    public enum Estado {
        INICIAL, SIGUIENTE, INHABILITADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "codigobarras")
    private Producto producto;


    private double costo_compra;
    private int cantidad;
    Estado estado;
    private LocalDateTime fecha;
    private int cantidad_vendida;




}
