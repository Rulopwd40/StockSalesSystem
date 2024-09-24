package com.libcentro.demo.repository;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.libcentro.demo.model.Producto;

import java.util.List;

@Repository
public interface IproductoRepository extends JpaRepository<Producto, String> {

    @Query("SELECT p FROM Producto p LEFT JOIN FETCH p.historial_costos hCosto LEFT JOIN FETCH p.historial_precios hPrecio WHERE p.codigo_barras = :codigoBarras")
    Producto findByCodigoBarrasWithHistorial(@Param("codigoBarras") String codigoBarras);

    @Query("SELECT p FROM Producto p WHERE p.codigo_barras = :codigoBarras")
    Producto findByCodigoBarras(@Param("codigoBarras") String codigoBarras);


}