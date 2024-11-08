package com.libcentro.demo.repository;

import com.libcentro.demo.model.VentaProductoId;
import com.libcentro.demo.model.Venta_Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IventaproductoRepository extends JpaRepository<Venta_Producto, VentaProductoId> {

    @Query("SELECT vp FROM Venta_Producto vp WHERE (:codigo_barras IS NULL OR vp.codigo_barras = :codigo_barras) AND vp.venta.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Venta_Producto> findByCodigo_barrasAndVentaFechaBetween(
            @Param("codigo_barras") String codigo_barras,
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin
    );

}
