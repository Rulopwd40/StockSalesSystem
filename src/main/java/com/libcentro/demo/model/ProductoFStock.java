package com.libcentro.demo.model;

import com.libcentro.demo.model.dto.ProductoFStockDTO;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@Entity
@Table(name="producto_fuera_de_stock")
@EqualsAndHashCode(callSuper=false)
public class ProductoFStock {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String nombre;
    @Column(columnDefinition = "NUMERIC")
    private double precio_venta;
    private int cantidad;
    private double descuento = 0;
    @ManyToOne
    @JoinColumn(name = "id_venta")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Venta venta;

    public ProductoFStock() {
    }

    public ProductoFStock ( ProductoFStockDTO productoFStockDTO ){
        this.nombre = productoFStockDTO.getNombre();
        this.precio_venta = productoFStockDTO.getPrecio_venta();
        this.cantidad = productoFStockDTO.getCantidad();
        this.descuento = productoFStockDTO.getDescuento();
    }

    public ProductoFStock ( double descuento, int cantidad, double precio_venta, String nombre, int id ){
        this.descuento = descuento;
        this.cantidad = cantidad;
        this.precio_venta = precio_venta;
        this.nombre = nombre;
        this.id = id;
    }
}
