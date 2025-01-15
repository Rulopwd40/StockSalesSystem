package com.libcentro.demo.model.dto;

import com.libcentro.demo.model.Producto;

import com.libcentro.demo.model.Venta_Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false, onlyExplicitlyIncluded=true)
@AllArgsConstructor
@NoArgsConstructor
public class Venta_ProductoDTO {

    @EqualsAndHashCode.Include
    private String id_venta;
    @EqualsAndHashCode.Include
    private String codigobarras;
    private ProductoDTO producto;
    private int cantidad;
    private double descuento;
    private double precio_venta;
    private double costo_compra;
    private double total;

    public Venta_ProductoDTO ( Venta_Producto vp ){
        this.id_venta = vp.getId_venta();
        this.codigobarras = vp.getCodigobarras();
        this.producto = new ProductoDTO(vp.getProducto());
        this.cantidad = vp.getCantidad();
        this.descuento = vp.getDescuento();
        this.precio_venta = vp.getPrecio_venta();
        this.costo_compra = vp.getCosto_compra();
        this.total = vp.getTotal();

    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
        updateTotal();
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        updateTotal();
    }
    public void setProducto(ProductoDTO producto, int cantidad){
        this.codigobarras = producto.getCodigobarras ();
        this.producto = producto;
        precio_venta = producto.getPrecio_venta();
        costo_compra = producto.getCosto_compra();
        this.cantidad = cantidad;
        updateTotal();
    }
    private void updateTotal() {
        total = precio_venta * cantidad - precio_venta * cantidad * descuento / 100;
        total = Math.round(total * 100.00f) / 100.00f;
    }
}
