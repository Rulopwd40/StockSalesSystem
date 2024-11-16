package com.libcentro.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class VentaProductoId implements Serializable {
    private long id_venta;      // campo entero de la clave
    private String codigo_barras; // campo String de la clave

    // Constructor sin argumentos
    public VentaProductoId() {}

    // Constructor con argumentos
    public VentaProductoId(int idInt, String idString) {
        this.id_venta = idInt;
        this.codigo_barras = idString;
    }

    // Getters y Setters
    public long getIdInt() {
        return id_venta;
    }

    public void setIdInt(int idInt) {
        this.id_venta = idInt;
    }

    public String getIdString() {
        return codigo_barras;
    }

    public void setIdString(String idString) {
        this.codigo_barras = idString;
    }

    // Sobreescribe equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VentaProductoId that = (VentaProductoId) o;
        return id_venta == that.id_venta && codigo_barras.equals(that.codigo_barras);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_venta, codigo_barras);
    }
}