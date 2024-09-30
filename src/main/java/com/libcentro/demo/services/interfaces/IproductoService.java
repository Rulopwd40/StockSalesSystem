package com.libcentro.demo.services.interfaces;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.libcentro.demo.model.Categoria;
import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import dto.UpdateProductoPorcentajeDTO;
import jakarta.transaction.Transactional;

public interface IproductoService {
    ///Acá declaro SOLO LOS MÉTODOS DE LA INTERFAZ, NO LOS ATRIBUTOS
    //las interfaces del service nos sirven para tener un código más ordenado, y 
    //que en el service(productoService), no haya un quilombo de métodos.
    //realmente esto se podría saltear, pero queda más aesthethic...
    @Transactional
    public List<Producto> getAll();
    @Transactional
    public void saveProducto(Producto x);
    @Transactional
    Producto crearProducto(Producto producto);
    @Transactional
    public void deleteProducto(Producto x);
    @Transactional
    public void updateProducto(Producto x, HistorialPrecio historialPrecio, HistorialCosto historialCosto);

    void venderProducto(Producto producto, int cantidad);

    public Producto getProducto(String codigo_barras);

    Producto getProducto(String codigo_barras, int cantidad);

    Producto getProductoByName(String nombre);

    @Transactional
    UpdateProductoPorcentajeDTO updatePrecioPorCategoria(Categoria categoria, BigDecimal porcentaje);

    @Transactional
    UpdateProductoPorcentajeDTO updatePrecioGeneral(BigDecimal porcentaje);

    Set<Producto> getProductoPorCategoria(Categoria categoria);
}
