package com.libcentro.demo.model;


import com.libcentro.demo.model.dto.Venta_ProductoDTO;
import com.libcentro.demo.model.id.VentaProductoId;
import jakarta.persistence.*;
import lombok.Data;


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
        private int cantidad;
        private double descuento;
        private double precio_venta;
        private double costo_compra;
        private double total;


        public Venta_Producto(Venta venta) {
            this.venta = venta;
        }
        public Venta_Producto() {}



    public Venta_Producto ( Venta_ProductoDTO ventaProductoDTO ){
        this.id_venta = ventaProductoDTO.getId_venta();
        this.codigobarras = ventaProductoDTO.getCodigobarras();
        this.cantidad = ventaProductoDTO.getCantidad();
        this.descuento = ventaProductoDTO.getDescuento();
        this.precio_venta = ventaProductoDTO.getPrecio_venta();
        this.costo_compra = ventaProductoDTO.getCosto_compra();
        this.total = ventaProductoDTO.getTotal();
    }

    public void setDescuento(float descuento) {
            this.descuento = descuento;
            updateTotal();
        }

    public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
            updateTotal();
    }
        public void setVenta(Venta venta) {
            this.venta = venta;
            this.id_venta = venta.getId();
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

            total = precio_venta * cantidad - precio_venta * cantidad * descuento / 100;

            // Redondear a 2 decimales
            total = Math.round(total * 100.00d) / 100.00d;
        }

    public long getId_venta (){
        return id_venta;
    }

    public String getCodigobarras (){
        return codigobarras;
    }

    public Venta getVenta (){
        return venta;
    }

    public Producto getProducto (){
        return producto;
    }

    public int getCantidad (){
        return cantidad;
    }

    public double getDescuento (){
        return descuento;
    }

    public double getPrecio_venta (){
        return precio_venta;
    }

    public double getCosto_compra (){
        return costo_compra;
    }

    public double getTotal (){
        return total;
    }
}
