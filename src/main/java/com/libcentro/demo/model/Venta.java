package com.libcentro.demo.model;

import com.libcentro.demo.model.dto.ProductoFStockDTO;
import com.libcentro.demo.model.dto.VentaDTO;
import com.libcentro.demo.model.dto.Venta_ProductoDTO;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Entity
@Table(name = "venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fecha;
    private double total;
    private boolean estado;


    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Venta_Producto> venta_productos = new HashSet<>();

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductoFStock> productoFStocks = new HashSet<>();

    public Venta() {
    }

    public Venta ( VentaDTO ventaDTO ){
        this.id = ventaDTO.getId();
        this.fecha = ventaDTO.getFecha();
        this.total = ventaDTO.getTotal();
        this.estado = ventaDTO.isEstado();
    }

    public long getId (){
        return id;
    }

    public void setId ( long id ){
        this.id = id;
    }

    public String getFecha (){
        return fecha;
    }

    public void setFecha ( String fecha ){
        this.fecha = fecha;
    }

    public double getTotal (){
        return total;
    }

    public void setTotal ( double total ){
        this.total = total;
    }

    public boolean isEstado (){
        return estado;
    }

    public void setEstado ( boolean estado ){
        this.estado = estado;
    }

    public Set<Venta_Producto> getVenta_productos (){
        return venta_productos;
    }

    public void setVenta_productos ( Set<Venta_Producto> venta_productos ){
        this.venta_productos = venta_productos;
    }

    public Set<ProductoFStock> getProductoFStocks (){
        return productoFStocks;
    }

    public void setProductoFStocks ( Set<ProductoFStock> productoFStocks ){
        this.productoFStocks = productoFStocks;
    }
}

