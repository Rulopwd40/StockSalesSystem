package com.libcentro.demo.model.dto;



import com.libcentro.demo.model.Producto;
import com.libcentro.demo.model.ProductoFStock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaDTO {


    private long id;
    private String fecha;
    private double total = 0;
    private boolean estado = true;
    private Set<Venta_ProductoDTO> venta_producto;
    private Set<ProductoFStockDTO> producto_fstock;

    public void addProducto(ProductoDTO p, int cantidad){
        Venta_ProductoDTO v = new Venta_ProductoDTO(this);
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
            total+= totalProducto - totalProducto * p.getDescuento()/100;
        }
        total = Math.round(total * 100.0f) / 100.0f;
    }


    public Set<Object> getTodosLosProductos(){
        Set<Object> productos = new HashSet<> ();

        productos.addAll(venta_producto);
        productos.addAll(producto_fstock);
        return productos;
    };

}
