package com.libcentro.demo.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "Venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(name = "fecha")
    public LocalDate fecha;
    @Column(name = "total")
    public float total;


    @ManyToMany
    @JoinTable(
        name = "venta_producto",
        joinColumns = @JoinColumn(name = "ventas_id"),
        inverseJoinColumns = @JoinColumn(name = "codigo_barras")
    )
    private Set<Producto> lista;


    public Venta() {
        total=0;
        lista = new HashSet<>();
    }

    public void addProducto(Producto p){
        lista.add(p);
    }


}

