package com.libcentro.demo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name="historial_costos")
public class HistorialCosto {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "id")),
            @AttributeOverride(name = "codigoBarrasc", column = @Column(name = "codigo_barras"))
    })
    private HistorialCostoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("codigoBarrasc")
    @JoinColumn(name = "codigo_barras", referencedColumnName = "codigo_barras", insertable = false, updatable = false)
    private Producto producto;
    @Column(name = "costo_compra")
    private double costo_compra;
    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "estado")
    boolean estado;
    @Column(name = "fecha")
    private String fecha;

    public HistorialCosto(Producto producto, double costo_compra, int cantidad) {
        this.producto =producto;
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

    public void setCosto_compra(double costo_compra) {
        this.costo_compra = costo_compra;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }



    public Integer getId() {
        return id.getId();
    }

    public String getFecha() {
        return fecha;
    }

    public boolean isEstado() {
        return estado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getCosto_compra() {
        return costo_compra;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setId(Integer id) {
        this.id.setId (id);
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
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
