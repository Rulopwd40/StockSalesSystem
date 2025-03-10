package com.libcentro.demo.repository;

import com.libcentro.demo.model.HistorialPrecio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IhistorialPrecioRepository extends JpaRepository<HistorialPrecio, Long> {

    @Query("SELECT h FROM HistorialPrecio h WHERE h.producto.codigobarras = :codigobarras ORDER BY h.id desc LIMIT 1")
    HistorialPrecio findFirstByProductoCodigobarrasOrderByIdDesc ( @Param("codigobarras") String codigobarras);

    @Query("SELECT h from HistorialPrecio h " +
            "WHERE h.producto.codigobarras = :codigobarras " +
            "order by h.fecha DESC "
    )
    List<HistorialPrecio> findAllByProducto( String codigobarras);

    @Modifying
    @Query("DELETE FROM HistorialPrecio h WHERE h.producto.codigobarras = :codigobarras")
    void deleteAllByProducto ( @Param("codigobarras") String codigobarras );
}
