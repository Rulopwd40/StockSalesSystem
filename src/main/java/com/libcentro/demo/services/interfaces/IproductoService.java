package com.libcentro.demo.services.interfaces;
import java.util.List;
import java.util.Optional;

import com.libcentro.demo.model.Producto;

import com.libcentro.demo.model.dto.CategoriaDTO;
import com.libcentro.demo.model.dto.PageDTO;
import com.libcentro.demo.model.dto.ProductoDTO;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;

public interface IproductoService {
    public List<ProductoDTO> getAll();
    @Transactional
    public Producto saveProducto ( Producto x);

    ProductoDTO crearProducto( ProductoDTO productoDTO);

    @Transactional
    boolean importarCSV(String path);

    void deleteProductoByCodigo(String codigo_barras);

    PageDTO<ProductoDTO> productosByPage( int page, String filter, boolean checkbox, int pagesize, boolean categoria);
    @Transactional
    public void updateProducto(ProductoDTO x);

    @Transactional
    void updateProductosBy( CategoriaDTO categoria, double porcentaje);

    ProductoDTO getProductoDTO ( String codigo_barras) throws ObjectNotFoundException;

    ProductoDTO getProductoDTO ( String codigo_barras, int cantidad);

    void undo();

    void undoAll();

    boolean save();

    List<ProductoDTO> getProductosByCantidad(int cantidad);

    void venderProducto ( ProductoDTO productoDTO, int cantidad );

    void actualizarCategorias ( List<ProductoDTO> productosSeleccionados, String string );

    void updatePrecio ( List<ProductoDTO> productosDTO, double porcentaje );

    Producto getProducto ( String codigobarras );

    boolean cambios ();

    void reembolsarProducto ( Producto producto, int cantidadReembolsar );

    void setCategoriaNull ( long id );
}
