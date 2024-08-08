package com.libcentro.demo.service.interfaces;
import java.util.List;
import com.libcentro.demo.model.producto;
public interface IproductoService {
    ///Acá declaro SOLO LOS MÉTODOS DE LA INTERFAZ, NO LOS ATRIBUTOS
    //las interfaces del service nos sirven para tener un código más ordenado, y 
    //que en el service(productoService), no haya un quilombo de métodos.
    //realmente esto se podría saltear, pero queda más aesthethic...
    public List<producto> getAll();
    public void saveProducto(producto x);
    public void deleteProducto(producto x);
    public void updateProducto(producto x);
    
}
