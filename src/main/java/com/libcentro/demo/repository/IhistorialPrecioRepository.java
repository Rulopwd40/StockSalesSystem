package com.libcentro.demo.repository;

import com.libcentro.demo.model.HistorialPrecio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IhistorialPrecioRepository extends JpaRepository<HistorialPrecio, Long> {

    @Query("SELECT h FROM HistorialPrecio h WHERE h.producto.codigobarras = :codigobarras ORDER BY h.id DESC LIMIT 1")
    HistorialPrecio findFirstByProductoCodigobarrasOrderByIdDesc ( @Param("codigobarras") String codigobarras);

}
