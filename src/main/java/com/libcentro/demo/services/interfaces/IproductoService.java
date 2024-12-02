package com.libcentro.demo.services.interfaces;
import java.util.List;

import com.libcentro.demo.model.Producto;

import com.libcentro.demo.model.dto.CategoriaDTO;
import com.libcentro.demo.model.dto.ProductoDTO;
import jakarta.transaction.Transactional;

public interface IproductoService {

    public List<Producto> getAll();
    @Transactional
    public Producto saveProducto( Producto x);

    Producto crearProducto(ProductoDTO producto);

    @Transactional
    boolean importarCSV(String path);

    void deleteProducto(Producto x);

    void deleteProductoByCodigo(String codigo_barras);

    void deleteProducto(String codigo_barras);

    @Transactional
    void updateProducto(ProductoDTO x);

    void updateProductosBy( CategoriaDTO categoria, double porcentaje);

    Producto getProducto(String codigo_barras);

    Producto getProducto(String codigo_barras, int cantidad);

    void undo();

    void undoAll();

    void save();

    List<Producto> getProductosByCantidad(int cantidad);
}
