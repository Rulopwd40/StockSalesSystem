package com.libcentro.demo.model;

import com.libcentro.demo.model.dto.ProductoFStockDTO;
import com.libcentro.demo.model.dto.VentaDTO;
import com.libcentro.demo.model.dto.Venta_ProductoDTO;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime fecha;
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


}

