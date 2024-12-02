package com.libcentro.demo.model;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
@Table(name="producto_fuera_de_stock")
public class ProductoFStock {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @Column(name="nombre")
    private String nombre;
    @Column(name="precio_venta")
    private float precio_venta;
    @Column(name="cantidad")
    private int cantidad;
    @Column(name="descuento")
    private float descuento;



    @ManyToOne
    @JoinColumn(name = "id_venta")
    private Venta venta;

    public ProductoFStock() {
        descuento=0;
    }

    public ProductoFStock(String nombre, String cantidad, String precio, String descuento) {
        this.nombre = nombre;
        this.cantidad = Integer.parseInt(cantidad);
        this.precio_venta = Float.parseFloat(precio);
        this.descuento = Float.parseFloat(descuento);
    }



}
