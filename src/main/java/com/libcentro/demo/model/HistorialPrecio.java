package com.libcentro.demo.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name="historial_precios")
public class HistorialPrecio {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "id")),
            @AttributeOverride(name = "codigoBarrasp", column = @Column(name = "codigo_barras"))
    })
    private HistorialPrecioId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("codigoBarrasp")
    @JoinColumn(name = "codigo_barras", referencedColumnName = "codigo_barras", insertable = false, updatable = false)
    private Producto producto;

    @Column(name="precio_venta")
    private double precio_venta;



    @Column(name="fecha")
    private String fecha;

    public HistorialPrecio(Producto producto, double precio_venta) {
        this.producto = producto;
        this.precio_venta = precio_venta;
        this.fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public HistorialPrecio() {
    }

    public long getId() {
        return id.getId ();
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(double precio_venta) {
        this.precio_venta = precio_venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Si son la misma instancia
        if (o == null || getClass() != o.getClass()) return false; // Si el objeto es de otro tipo

        HistorialPrecio historialPrecio = (HistorialPrecio) o;

        // Comparamos por codigo_barras o por algún otro identificador único
        return Objects.equals(id, historialPrecio.getId());
    }


}
