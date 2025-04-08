package com.libcentro.demo.repository;


import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IhistorialCostoRepository extends JpaRepository<HistorialCosto, Long> {

    @Query("SELECT h FROM HistorialCosto h WHERE h.producto.codigobarras = :codigobarras ORDER BY h.id DESC LIMIT 1")
    HistorialCosto findFirstByProductoCodigobarrasOrderByIdDesc ( @Param("codigobarras") String codigobarras);

    @Query("SELECT h FROM HistorialCosto h WHERE h.producto.codigobarras = :codigobarras AND h.estado = :estado")
    HistorialCosto findInicial(String codigobarras,HistorialCosto.Estado estado);

    @Query("SELECT h FROM HistorialCosto h " +
            "WHERE h.producto.codigobarras = :codigobarras " +
            "AND h.estado = :estado " +
            "ORDER BY h.fecha ASC " +
            "LIMIT 1")
    HistorialCosto findNext(String codigobarras,HistorialCosto.Estado estado);

    @Query("SELECT h from HistorialCosto h " +
            "WHERE h.producto.codigobarras = :codigobarras " +
            "order by h.fecha DESC "
            )
    List<HistorialCosto> findAllByProducto( String codigobarras);

    @Query("SELECT h FROM HistorialCosto h WHERE h.fecha < :fecha AND h.producto.codigobarras = :codigo_barras ORDER BY h.fecha DESC LIMIT 1")
    HistorialCosto findAnteriorByFecha(@Param("fecha") LocalDateTime fecha,String codigo_barras);

    @Query("SELECT h FROM HistorialCosto h WHERE h.fecha > :fecha AND h.producto.codigobarras = :codigo_barras ORDER BY h.fecha ASC LIMIT 1")
    HistorialCosto findSiguienteByFecha(@Param("fecha") LocalDateTime fecha, @Param("codigo_barras") String codigo_barras);

    @Modifying
    @Query("DELETE FROM HistorialCosto h WHERE h.producto.codigobarras = :codigobarras")
    void deleteAllByProducto(@Param("codigobarras") String codigobarras);

    @Query("SELECT h FROM HistorialCosto h " +
            "WHERE h.producto.codigobarras = :codigobarras " +
            "ORDER BY h.fecha DESC")
    Page<HistorialCosto> findByProductoCodigobarras( @Param("codigobarras") String codigobarras, Pageable pageable);
}
