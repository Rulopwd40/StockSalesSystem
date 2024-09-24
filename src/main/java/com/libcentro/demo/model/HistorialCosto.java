package com.libcentro.demo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name="historial_costos")
public class HistorialCosto {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="codigo_barras", referencedColumnName = "codigo_barras")
    private Producto producto;
    @Column(name = "costo_compra")
    private float costo_compra;
    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "estado")
    boolean estado;
    @Column(name = "fecha")
    private String fecha;

    public HistorialCosto(Producto producto, float costo_compra, int cantidad) {
        this.producto = producto;
        this.costo_compra = costo_compra;
        this.cantidad = cantidad;
        this.estado = true;
        this.fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public HistorialCosto() {

    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setCosto_compra(float costo_compra) {
        this.costo_compra = costo_compra;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Si son la misma instancia
        if (o == null || getClass() != o.getClass()) return false; // Si el objeto es de otro tipo

        HistorialCosto historialCosto = (HistorialCosto) o;

        // Comparamos por codigo_barras o por algún otro identificador único
        return Objects.equals(id, historialCosto.getId());
    }



}
