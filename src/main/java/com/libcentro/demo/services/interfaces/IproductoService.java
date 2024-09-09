package com.libcentro.demo.services.interfaces;
import java.util.List;
import com.libcentro.demo.model.Producto;
public interface IproductoService {
    ///Acá declaro SOLO LOS MÉTODOS DE LA INTERFAZ, NO LOS ATRIBUTOS
    //las interfaces del service nos sirven para tener un código más ordenado, y 
    //que en el service(productoService), no haya un quilombo de métodos.
    //realmente esto se podría saltear, pero queda más aesthethic...
    public List<Producto> getAll();
    public void saveProducto(Producto x);
    public void deleteProducto(Producto x);
    public void updateProducto(Producto x);
    
}
