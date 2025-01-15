package com.libcentro.demo.model;


import com.libcentro.demo.model.dto.Venta_ProductoDTO;
import com.libcentro.demo.model.id.VentaProductoId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name="venta_producto")
@IdClass(VentaProductoId.class)
public class Venta_Producto {

        @Id
        private String id_venta;

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
        @Column(columnDefinition = "NUMERIC")
        private double precio_venta;
        @Column(columnDefinition = "NUMERIC")
        private double costo_compra;
        @Column(columnDefinition = "NUMERIC")
        private double total;


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

                total = Math.round(total * 100.00d) / 100.00d;
            }

}
