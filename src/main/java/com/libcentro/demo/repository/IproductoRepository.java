package com.libcentro.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.libcentro.demo.model.Producto;

@Repository
public interface IproductoRepository extends JpaRepository<Producto, String> {

    @Query("SELECT p FROM Producto p LEFT JOIN FETCH p.historial_precios LEFT JOIN FETCH p.historial_costos WHERE p.codigo_barras = :codigoBarras")
    Producto findByCodigoBarrasConHistorial(@Param("codigoBarras") String codigoBarras);


}