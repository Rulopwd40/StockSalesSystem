package com.libcentro.demo.model;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;
import java.util.*;

@Data
@Entity
@Table(name = "venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fecha;
    private double total;
    private boolean estado;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Venta_Producto> listaProductos = new HashSet<>();

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductoFStock> listaProductosF = new HashSet<>();

    public Venta() {
    }

}

