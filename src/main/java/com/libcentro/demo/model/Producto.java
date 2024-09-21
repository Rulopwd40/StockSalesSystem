package com.libcentro.demo.model;

import jakarta.persistence.*;


import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Producto")
public class Producto {
    @Id
    private String codigo_barras;
    @Column(name = "nombre")
    private String nombre;
    @ManyToOne(fetch = FetchType.EAGER) // O EAGER según lo que prefieras
    @JoinColumn(name = "categoria", referencedColumnName = "id")
    private Categoria categoria;
    @Column(name = "costo_compra")
    private float costo_compra;
    @Column(name = "precio_venta")
    private float precio_venta;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="historial_precios",referencedColumnName = "id")
    HistorialPrecio costo_inicial;
    @Column(name = "stock")
    private int stock;




    public Producto() {
    }


    public Producto(String codigo_barras,String nombre,Categoria categoria, float costo_compra, float precio_venta, int stock) {
        this.codigo_barras = codigo_barras;
        this.nombre = nombre;
        this.categoria = categoria;
        this.costo_compra = costo_compra;
        this.precio_venta = precio_venta;
        this.stock = stock;

    }





    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public Categoria getCategoria() {
        return categoria;
    }


    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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

    public HistorialPrecio getCosto_inicial() {
        return costo_inicial;
    }

    public void setCosto_inicial(HistorialPrecio costo_inicial) {
        this.costo_inicial = costo_inicial;
    }
}
