package com.libcentro.demo.model;

public class producto {
    public int id;
    public String nombre;
    public String descripcion;
    public float costo_compra;
    public float precio_venta;
    public int stock;
    public String codigo_barras;

    
    public producto() {
    }


    public producto(int id, String nombre, String descripcion, float costo_compra, float precio_venta, int stock,
            String codigo_barras) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo_compra = costo_compra;
        this.precio_venta = precio_venta;
        this.stock = stock;
        this.codigo_barras = codigo_barras;
    }

}
