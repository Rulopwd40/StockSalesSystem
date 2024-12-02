package com.libcentro.demo.model;


import com.libcentro.demo.model.id.VentaProductoId;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="venta_producto")
@IdClass(VentaProductoId.class)
public class Venta_Producto {

        @Id
        private long id_venta;

        @Id
        private String codigobarras;

        @ManyToOne
        @JoinColumn(name = "id_venta", insertable = false, updatable = false)
        private Venta venta;

        @ManyToOne
        @JoinColumn(name = "codigobarras", insertable = false, updatable = false)
        private Producto producto;

        @Column(name="cantidad")
        private int cantidad;

        @Column(name="descuento")
        private double descuento;

        @Column(name="precio_venta")
        private double precio_venta;

        @Column(name="costo_compra")
        private double costo_compra;

        @Column(name="total")
        private double total;


    public Venta_Producto(Venta venta) {
        this.venta = venta;
    }

    public Venta_Producto() {

    }


    public void setDescuento(float descuento) {
        this.descuento = descuento;
        updateTotal();
    }



    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        updateTotal();
    }



    public void setProducto(Producto producto, int cantidad) {
        this.codigobarras = producto.getCodigobarras ();
        this.producto = producto;
        precio_venta = producto.getPrecio_venta();
        costo_compra = producto.getCosto_compra();
        this.cantidad = cantidad;
        updateTotal();
    }

    private void updateTotal() {
        // Calcular el total antes de redondear
        total = precio_venta * cantidad - precio_venta * cantidad * descuento / 100;

        // Redondear a 2 decimales
        total = Math.round(total * 100.00f) / 100.00f;
    }


}
