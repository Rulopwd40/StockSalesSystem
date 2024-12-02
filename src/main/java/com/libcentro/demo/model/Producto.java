package com.libcentro.demo.model;

import com.libcentro.demo.model.dto.ProductoDTO;
import jakarta.persistence.*;
import lombok.Data;


import java.util.*;

@Data
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    private String codigobarras;
    @Column(name = "nombre")
    private String nombre;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria", referencedColumnName = "id")
    private Categoria categoria;
    @Column(name = "costo_compra")
    private double costo_compra;
    @Column(name = "precio_venta")
    private double precio_venta;

    @Column(name = "costo_inicial")
    private Integer costo_inicial;

    @Column(name = "stock")
    private int stock;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<HistorialPrecio> historial_precios;
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<HistorialCosto> historial_costos;


    public Producto() {
    }


    public Producto(Producto producto) {
        this.codigobarras = producto.getCodigobarras ();
        this.nombre = producto.getNombre();
        this.categoria = producto.getCategoria();
        this.precio_venta = producto.getPrecio_venta();
        this.costo_compra = producto.getCosto_compra();
        this.stock = producto.getStock();
        historial_precios = new HashSet<>();
        historial_costos = new HashSet<>();

    }

    public Producto(ProductoDTO producto) {
        this.codigobarras = producto.getCodigobarras();
        this.nombre = producto.getNombre();
        this.categoria = producto.getCategoria();
        this.precio_venta = producto.getPrecio_venta();
        this.costo_compra = producto.getCosto_compra();
        this.stock = producto.getStock();
        historial_precios = new HashSet<>();
        historial_costos = new HashSet<>();
    }


    public Producto ( String codigobarras, String nombre, Categoria categoria, double costoCompra, double precioVenta, int stock ){
        this.codigobarras = codigobarras;
        this.nombre = nombre;
        this.categoria = categoria;
        this.costo_compra = costoCompra;
        this.precio_venta = precioVenta;
        this.stock = stock;
    }
}
