package com.libcentro.demo.services.interfaces;
import java.util.List;

import com.libcentro.demo.model.Producto;

import com.libcentro.demo.model.dto.CategoriaDTO;
import com.libcentro.demo.model.dto.ProductoDTO;
import com.libcentro.demo.model.dto.ProductoPageDTO;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;

public interface IproductoService {
    public List<ProductoDTO> getAll();
    @Transactional
    public Producto crearProducto ( Producto x);

    ProductoDTO crearProducto( ProductoDTO productoDTO);

    @Transactional
    boolean importarCSV(String path);

    void deleteProductoByCodigo(String codigo_barras);

    ProductoPageDTO productosByPage( int page, String filter, boolean checkbox,int pagesize);
    @Transactional
    public void updateProducto(ProductoDTO x);

    @Transactional
    void updateProductosBy( CategoriaDTO categoria, double porcentaje);

    ProductoDTO getProducto(String codigo_barras) throws ObjectNotFoundException;

    ProductoDTO getProducto(String codigo_barras, int cantidad);

    void undo();

    void undoAll();

    void save();

    List<ProductoDTO> getProductosByCantidad(int cantidad);

    void venderProducto ( ProductoDTO productoDTO, int cantidad );
}
