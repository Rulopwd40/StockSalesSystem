package com.libcentro.demo.repository;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IhistorialcostosRepository extends JpaRepository<HistorialCosto,Long> {

    @Query("SELECT hc FROM HistorialCosto hc WHERE hc.producto.codigo_barras = :codigo_barras order by hc.id DESC LIMIT 1")
    HistorialCosto findFirstByProductoOrderByIdDesc(String codigo_barras);

    Set<HistorialCosto> findAllByProductoOrderByIdDesc(Producto producto);
}
