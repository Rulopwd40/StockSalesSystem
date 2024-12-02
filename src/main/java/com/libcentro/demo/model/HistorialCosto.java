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
    @JoinColumn(name = "codigobarras") // Asegúrate de que esta columna exista en la tabla
    private Producto producto;


    private double costo_compra;

    private int cantidad;

    Estado estado;

    private String fecha;




}
