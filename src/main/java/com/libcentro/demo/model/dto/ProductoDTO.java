package com.libcentro.demo.model.dto;

import lombok.*;


@Data
@AllArgsConstructor
public class ProductoDTO {
    
    private String codigobarras;
    private String nombre;
    private CategoriaDTO categoria;
    private double costo_compra;
    private double precio_venta;
    private int stock;


}
