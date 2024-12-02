package com.libcentro.demo.model;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;
import java.util.*;

@Data
@Entity
@Table(name = "venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "fecha")
    private String fecha;
    @Column(name = "total")
    private double total;
    @Column(name = "estado")
    private boolean estado;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Venta_Producto> listaProductos = new HashSet<>();

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductoFStock> listaProductosF = new HashSet<>();

    public Venta() {
        total= 0;
        estado = true;
    }

    public void addProducto(Producto p,int cantidad){
        Venta_Producto v = new Venta_Producto(this);
        v.setProducto(p,cantidad);
        listaProductos.add(v);
    }
    public void addProducto(ProductoFStock p){
        listaProductosF.add(p);

    }

    public void updateTotal() {
        total=0;
        double totalProducto;
        for(Venta_Producto v : listaProductos){
            total+= v.getTotal();
        }

        for(ProductoFStock p : listaProductosF){
            totalProducto=p.getCantidad() * p.getPrecio_venta();
            total+= totalProducto - totalProducto * p.getDescuento()/100;
        }
        total = Math.round(total * 100.0f) / 100.0f;
    }



    public Set<Object> getTodosLosProductos(){
        Set<Object> productos = new HashSet<>();

        productos.addAll(listaProductos);
        productos.addAll(listaProductosF);
        return productos;
    };

}

