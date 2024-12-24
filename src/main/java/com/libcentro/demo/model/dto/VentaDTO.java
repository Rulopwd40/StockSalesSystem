package com.libcentro.demo.model.dto;



import com.libcentro.demo.model.Producto;
import com.libcentro.demo.model.ProductoFStock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaDTO {


    private long id;
    private LocalDateTime fecha;
    private double total = 0;
    private boolean estado = true;
    private Set<Venta_ProductoDTO> venta_producto = new HashSet<>();
    private Set<ProductoFStockDTO> producto_fstock = new HashSet<>();

    public void addProducto(ProductoDTO p, int cantidad){
        Venta_ProductoDTO v = new Venta_ProductoDTO();
        v.setProducto(p,cantidad);
        venta_producto.add(v);
    }
    public void addProducto(ProductoFStockDTO p){
        producto_fstock.add(p);
    }

    public void updateTotal() {
        total=0;
        double totalProducto;
        for(Venta_ProductoDTO v : venta_producto){
            total+= v.getTotal();
        }

        for(ProductoFStockDTO p : producto_fstock){
            totalProducto=p.getCantidad() * p.getPrecio_venta();
            total+= Math.round((totalProducto - totalProducto * p.getDescuento()/100)*100d) /100d;
        }
        total = Math.round(total * 100.0d) / 100.0d;
    }


    public Set<Object> getTodosLosProductos(){
        Set<Object> productos = new HashSet<> ();

        productos.addAll(venta_producto);
        productos.addAll(producto_fstock);
        return productos;
    };

}
