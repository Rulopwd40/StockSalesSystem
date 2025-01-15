package com.libcentro.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.libcentro.demo.model.Venta;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Repository
public interface IventaRepository extends JpaRepository<Venta, String> {

    @Modifying
    @Query("UPDATE Venta v SET v.estado = CASE WHEN v.estado = true THEN false ELSE true END WHERE v.id = :id")
    void toggleEstadoById(@Param("id") String id);

    @Query("SELECT COUNT(v) FROM Venta v WHERE v.fecha BETWEEN :inicioDelDia AND :finDelDia")
    public int countByFecha(LocalDateTime inicioDelDia, LocalDateTime finDelDia);

    @Query("SELECT v FROM Venta v " +
            "WHERE (:filterT IS NULL OR :filterT = '' OR v.id LIKE :filterT)" +
            "ORDER BY v.id DESC ")
    Page<Venta> findByIdStartingWithAndOrdered(Pageable pageable, @Param("filterT") String filterT);

}
