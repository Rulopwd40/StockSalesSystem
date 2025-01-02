package com.libcentro.demo.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false,onlyExplicitlyIncluded = true)
public class ProductoFStockDTO {

    private long id;
    @EqualsAndHashCode.Include
    private String nombre;
    private float precio_venta;
    private int cantidad;
    private float descuento;
    private VentaDTO venta;

    public ProductoFStockDTO(String nombre, String cantidad, String precio, String descuento) {
        this.nombre = nombre;
        this.cantidad = Integer.parseInt(cantidad);
        this.precio_venta = Float.parseFloat(precio);
        this.descuento = Float.parseFloat(descuento);
    }
}
