package com.libcentro.demo.model.estadisticas;

import java.util.Date;

public class Estadistica {

    private int cantidad;
    private float total;
    private float ganancia;
    private String fecha;


    public int getCantidad() {
        return cantidad;
    }

    public float getTotal() {
        return total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
