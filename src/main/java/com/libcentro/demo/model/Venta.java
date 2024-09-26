package com.libcentro.demo.model;

import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "fecha")
    private LocalDate fecha;
    @Column(name = "total")
    private float total;

    @OneToMany(mappedBy = "venta")
    private Set<Venta_Producto> listaProductos = new HashSet<>();

    @OneToMany(mappedBy = "venta")
    private Set<ProductoFStock> listaProductosF = new HashSet<>();

    public Venta() {
        total= 0;

    }

    public void addProducto(Producto p,int cantidad){
        Venta_Producto v = new Venta_Producto();
        v.setProducto(p,cantidad);
        listaProductos.add(v);


    }
    public void addProducto(ProductoFStock p){
        listaProductosF.add(p);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public float getTotal() {
        return total;
    }

    public void updateTotal() {
        total=0;
        float totalProducto;
        for(Venta_Producto v : listaProductos){
            total+= v.getTotal();
        }

        for(ProductoFStock p : listaProductosF){
            totalProducto=p.getCantidad() * p.getPrecio_venta();
            total+= totalProducto - totalProducto * p.getDescuento()/100;
        }
    }

    public Set<Venta_Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(Set<Venta_Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public Set<ProductoFStock> getListaProductosF() {
        return listaProductosF;
    }

    public void setListaProductosF(Set<ProductoFStock> listaProductosF) {
        this.listaProductosF = listaProductosF;
    }

    public Set<Object> getTodosLosProductos(){
        Set<Object> productos = new HashSet<>();

        productos.addAll(listaProductos);
        productos.addAll(listaProductosF);
        return productos;
    };

}

