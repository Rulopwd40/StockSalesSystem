package com.libcentro.demo.model;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.*;

@Entity
@Table(name="producto_fuera_de_stock")
public class ProductoFStock {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(name="nombre")
    private String nombre;
    @Column(name="precio_venta")
    private float precio_venta;
    @Column(name="cantidad")
    private int cantidad;
    @Column(name="descuento")
    private float descuento;



    @ManyToOne
    @JoinColumn(name = "venta_id")
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(float precio_venta) {
        this.precio_venta = precio_venta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }
}
