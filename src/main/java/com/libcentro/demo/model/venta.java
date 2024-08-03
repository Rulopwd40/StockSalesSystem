package com.libcentro.demo.model;

import java.util.*;

import org.apache.commons.lang3.time.DateFormatUtils;

public class venta {
    public producto Prod;
    public int id;
    public DateFormatUtils fecha;
    public float total;
    public List<producto> lista = new ArrayList<producto>();

    public venta() {
    }

    public venta(producto prod, int id, DateFormatUtils fecha, float total, List<producto> lista) {
        Prod = prod;
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.lista = lista;
    }


    
}
