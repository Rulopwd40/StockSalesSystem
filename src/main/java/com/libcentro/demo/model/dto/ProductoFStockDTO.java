package com.libcentro.demo.model.dto;


import com.libcentro.demo.model.ProductoFStock;
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
    private double precio_venta;
    private int cantidad;
    private double descuento;
    private VentaDTO venta;

    public ProductoFStockDTO(String nombre, String cantidad, String precio, String descuento) {
        this.nombre = nombre;
        this.cantidad = Integer.parseInt(cantidad);
        this.precio_venta = Float.parseFloat(precio);
        this.descuento = Float.parseFloat(descuento);
    }

    public ProductoFStockDTO ( ProductoFStock productoFStock ){
        this .id = productoFStock.getId();
        this.nombre = productoFStock.getNombre();
        this.cantidad = productoFStock.getCantidad();
        this.precio_venta = productoFStock.getPrecio_venta();
        this.descuento = productoFStock.getDescuento();

    }
}
