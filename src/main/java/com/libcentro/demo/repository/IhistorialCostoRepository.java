package com.libcentro.demo.repository;


import com.libcentro.demo.model.HistorialCosto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IhistorialCostoRepository extends JpaRepository<HistorialCosto, Long> {

    @Query("SELECT h FROM HistorialCosto h WHERE h.producto.codigobarras = :codigobarras ORDER BY h.id DESC LIMIT 1")
    HistorialCosto findFirstByProductoCodigobarrasOrderByIdDesc ( @Param("codigobarras") String codigobarras);
}
