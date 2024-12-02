package com.libcentro.demo.repository;

import com.libcentro.demo.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.libcentro.demo.model.Producto;

import java.util.List;

@Repository
public interface IproductoRepository extends JpaRepository<Producto, String> {

    List<Producto> findAllByCategoria(Categoria categoria);

    @Query("SELECT p FROM Producto p " +
            "LEFT JOIN FETCH p.historial_precios hp " +
            "LEFT JOIN FETCH p.historial_costos hc " +
            "WHERE p.codigobarras = :codigo")
    Producto getProductoWithHistorialPrecioAndHistorialCosto(@Param("codigo") String codigo_barras);

    List<Producto> findByStockLessThanEqual(int cantidad);

}
