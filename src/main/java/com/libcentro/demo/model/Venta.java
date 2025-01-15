package com.libcentro.demo.model;

import com.libcentro.demo.model.dto.ProductoFStockDTO;
import com.libcentro.demo.model.dto.VentaDTO;
import com.libcentro.demo.model.dto.Venta_ProductoDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "venta")
public class Venta {

    @Id
    private String id;
    private LocalDateTime fecha;
    @Column(columnDefinition = "NUMERIC")
    private double total;
    private boolean estado;



    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Venta_Producto> venta_productos = new HashSet<>();

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
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

