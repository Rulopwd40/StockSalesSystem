package dto;

import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class UpdateProductoPorcentajeDTO {
    List<Producto> productoList;
    int cantProductos;
    Set<HistorialPrecio> historialPrecio;
    float porcentaje;

    public UpdateProductoPorcentajeDTO() {}

    public UpdateProductoPorcentajeDTO(List<Producto> productoList, int cantProductos, Set<HistorialPrecio> historialPrecio, BigDecimal porcentaje) {
        this.productoList = productoList;
        this.cantProductos = cantProductos;
        this.historialPrecio = historialPrecio;
    }

    public List<Producto> getProductoList() {
        return productoList;
    }
    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }
    public int getCantProductos() {
        return cantProductos;
    }

}
