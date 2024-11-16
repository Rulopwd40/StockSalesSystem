package com.libcentro.demo.model.dto;

import com.libcentro.demo.model.Categoria;

public class ProductoDTO {



    private String codigo_barras;
      private String nombre;
      private Categoria categoria;
      private double costo_compra;
      private double precio_venta;
      private int stock;


    public ProductoDTO(String codigo_barras, String nombre, Categoria categoria, double costo_compra, double precio_venta, int stock) {
        this.codigo_barras = codigo_barras;
        this.nombre = nombre;
        this.categoria = categoria;
        this.costo_compra = costo_compra;
        this.precio_venta = precio_venta;
        this.stock = stock;
    }

    public ProductoDTO (){

    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(double precio_venta) {
        this.precio_venta = precio_venta;
    }

    public double getCosto_compra() {
        return costo_compra;
    }

    public void setCosto_compra(double costo_compra) {
        this.costo_compra = costo_compra;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo_barras() {
        return codigo_barras;
    }

    public void setCodigo_barras(String codigo_barras) {
        this.codigo_barras = codigo_barras;
    }
}
