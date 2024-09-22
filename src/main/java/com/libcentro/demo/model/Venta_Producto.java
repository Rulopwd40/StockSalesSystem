package com.libcentro.demo.model;


import jakarta.persistence.*;

@Entity
@Table(name="venta_producto")
public class Venta_Producto {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @ManyToOne
        @JoinColumn(name = "venta_id")
        private Venta venta;

        @ManyToOne
        @JoinColumn(name = "codigo_barras")
        private Producto producto;

        @Column(name="cantidad")
        private int cantidad;

        @Column(name="descuento")
        private float descuento;

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }




}
