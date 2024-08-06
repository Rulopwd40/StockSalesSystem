package com.libcentro.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "Producto")
public class producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @Column(name = "nombre")
    public String nombre;
    @Column(name = "descripcion")
    public String descripcion;
    @Column(name = "costo_compra")
    public float costo_compra;
    @Column(name = "precio_venta")
    public float precio_venta;
    @Column(name = "stock")
    public int stock;
    @Column(name = "codigo_barras")
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


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public float getCosto_compra() {
        return costo_compra;
    }


    public void setCosto_compra(float costo_compra) {
        this.costo_compra = costo_compra;
    }


    public float getPrecio_venta() {
        return precio_venta;
    }


    public void setPrecio_venta(float precio_venta) {
        this.precio_venta = precio_venta;
    }


    public int getStock() {
        return stock;
    }


    public void setStock(int stock) {
        this.stock = stock;
    }


    public String getCodigo_barras() {
        return codigo_barras;
    }


    public void setCodigo_barras(String codigo_barras) {
        this.codigo_barras = codigo_barras;
    }
    
}
