package com.libcentro.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.libcentro.demo.model.Venta;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Repository
public interface IventaRepository extends JpaRepository<Venta, Integer> {

    @Query ("UPDATE Venta v SET v.estado = false WHERE v.id= :id")
    public void eliminacionLogica(long id);

    @Query("SELECT COUNT(v) FROM Venta v WHERE v.fecha BETWEEN :inicioDelDia AND :finDelDia")
    public int countByFecha(LocalDateTime inicioDelDia, LocalDateTime finDelDia);
}
