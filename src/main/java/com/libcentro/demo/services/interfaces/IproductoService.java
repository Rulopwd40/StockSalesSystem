package com.libcentro.demo.services.interfaces;
import java.util.List;

import com.libcentro.demo.model.Categoria;
import com.libcentro.demo.model.Producto;
import jakarta.transaction.Transactional;
import com.libcentro.demo.model.HistorialPrecio;

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
    public void updateProducto(Producto x);

    void venderProducto(Producto producto, int cantidad);

    public Producto getProducto(String codigo_barras);

    Producto getProducto(String codigo_barras, int cantidad);
}
