package com.libcentro.demo.model;

import com.libcentro.demo.model.dto.ProductoFStockDTO;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name="producto_fuera_de_stock")
public class ProductoFStock {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String id;
    private String nombre;
    @Column(columnDefinition = "NUMERIC")
    private double precio_venta;
    private int cantidad;
    private double descuento = 0;
    @ManyToOne
    @JoinColumn(name = "id_venta")
    private Venta venta;

    public ProductoFStock() {
    }

    public ProductoFStock ( ProductoFStockDTO productoFStockDTO ){
        this.nombre = productoFStockDTO.getNombre();
        this.precio_venta = productoFStockDTO.getPrecio_venta();
        this.cantidad = productoFStockDTO.getCantidad();
        this.descuento = productoFStockDTO.getDescuento();
    }
}
