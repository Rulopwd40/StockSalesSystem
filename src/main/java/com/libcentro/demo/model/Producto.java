package com.libcentro.demo.model;

import com.libcentro.demo.model.dto.ProductoDTO;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    private String codigobarras;
    private String nombre;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria", referencedColumnName = "id", nullable = true)
    private Categoria categoria;
    @Column(columnDefinition = "NUMERIC")
    private double costo_compra;
    @Column(columnDefinition = "NUMERIC")
    private double precio_venta;
    private int stock;
    private boolean eliminado;
    @Column(nullable = true)
    private LocalDateTime fechaEliminacion;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistorialPrecio> historial_precios = new ArrayList<>();
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistorialCosto> historial_costos = new ArrayList<>();

    public Producto() {
    }

    public Producto(Producto producto) {
        this.codigobarras = producto.getCodigobarras ();
        this.nombre = producto.getNombre();
        this.categoria = producto.getCategoria();
        this.precio_venta = producto.getPrecio_venta();
        this.costo_compra = producto.getCosto_compra();
        this.stock = producto.getStock();
        this.historial_precios = producto.getHistorial_precios ();
        this.historial_costos = producto.getHistorial_costos ();
    }

    public Producto(ProductoDTO producto) {
        this.codigobarras = producto.getCodigobarras();
        this.nombre = producto.getNombre();
        this.categoria = new Categoria (producto.getCategoria());
        this.precio_venta = Math.round(producto.getPrecio_venta() * 100d) / 100d;
        this.costo_compra = Math.round(producto.getCosto_compra() * 100d) / 100d;
        this.stock = producto.getStock();
        this.historial_precios = new ArrayList<>();
        this.historial_costos = new ArrayList<>();
    }

    public Producto ( String codigobarras, String nombre, Categoria categoria, double costoCompra, double precioVenta, int stock ){
        this.codigobarras = codigobarras;
        this.nombre = nombre;
        this.categoria = categoria;
        this.costo_compra = costoCompra;
        this.precio_venta = precioVenta;
        this.stock = stock;
        this.historial_precios = new ArrayList<>();
        this.historial_costos = new ArrayList<>();
    }
}
