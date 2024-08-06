package com.libcentro.demo.model;

import java.util.*;
import javax.persistence.*;

import org.apache.commons.lang3.time.DateFormatUtils;

@Entity
@Table(name = "Venta")
public class venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(name = "fecha")
    public DateFormatUtils fecha;
    @Column(name = "total")
    public float total;
    public producto Prod;
    
    @ManyToMany
    @JoinTable(
        name = "ventas_productos",
        joinColumns = @JoinColumn(name = "ventas_id"),
        inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<producto> lista = new ArrayList<>();

    public venta() {
    }

    public venta(producto prod, int id, DateFormatUtils fecha, float total, List<producto> lista) {
        Prod = prod;
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.lista = lista;
    }


    
}
