package com.libcentro.demo.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name="historial_precios")
public class HistorialPrecio {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="producto", referencedColumnName = "codigo_barras")
    private Producto producto;
    @Column(name = "costo_compra")
    private float costo_compra;
    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "fecha")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Date fecha;



    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
