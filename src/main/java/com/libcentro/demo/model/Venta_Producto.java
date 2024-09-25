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

        @Column(name="precio_venta")
        private float precio_venta;

        @Column(name="total")
        private float total;

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
        updateTotal();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        updateTotal();
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

    public float getTotal() {
        return total;
    }

    public void setProducto(Producto producto, int cantidad) {
        this.producto = producto;
        precio_venta = producto.getPrecio_venta();
        this.cantidad = cantidad;
        updateTotal();
    }

    private void updateTotal() {
        total = precio_venta * cantidad - precio_venta * cantidad * descuento/100;
    };

    public float getPrecio_venta() {
        return precio_venta;
    }
}
