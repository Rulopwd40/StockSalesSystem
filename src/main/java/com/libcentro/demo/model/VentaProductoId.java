package com.libcentro.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class VentaProductoId implements Serializable {
    private int idInt;      // campo entero de la clave
    private String idString; // campo String de la clave

    // Constructor sin argumentos
    public VentaProductoId() {}

    // Constructor con argumentos
    public VentaProductoId(int idInt, String idString) {
        this.idInt = idInt;
        this.idString = idString;
    }

    // Getters y Setters
    public int getIdInt() {
        return idInt;
    }

    public void setIdInt(int idInt) {
        this.idInt = idInt;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    // Sobreescribe equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VentaProductoId that = (VentaProductoId) o;
        return idInt == that.idInt && idString.equals(that.idString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInt, idString);
    }
}