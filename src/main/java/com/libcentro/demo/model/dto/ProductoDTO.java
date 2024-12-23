package com.libcentro.demo.model.dto;

import com.libcentro.demo.model.Producto;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    
    private String codigobarras;
    private String nombre;
    private CategoriaDTO categoria;
    private double costo_compra;
    private double precio_venta;
    private int stock;

    public ProductoDTO ( Producto producto) {
        this.codigobarras = producto.getCodigobarras();
        this.nombre = producto.getNombre();
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setNombre(producto.getCategoria().getNombre());
        categoriaDTO.setId (producto.getCategoria().getId());
        this.categoria = categoriaDTO;
        this.costo_compra = producto.getCosto_compra();
        this.precio_venta = producto.getPrecio_venta();
        this.stock = producto.getStock();

    }


}
