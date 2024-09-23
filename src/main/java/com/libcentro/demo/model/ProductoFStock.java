package com.libcentro.demo.model;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.*;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Si son la misma instancia
        if (o == null || getClass() != o.getClass()) return false; // Si el objeto es de otro tipo

        ProductoFStock producto = (ProductoFStock) o;

        // Comparamos por codigo_barras o por algún otro identificador único
        return Objects.equals(nombre, producto.getNombre());
    }

    @Override
    public int hashCode() {
        return nombre != null ? nombre.hashCode() : 0;
    }
}
