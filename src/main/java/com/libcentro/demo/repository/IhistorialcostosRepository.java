package com.libcentro.demo.repository;

import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IhistorialcostosRepository extends JpaRepository<HistorialCosto,Long> {

    HistorialCosto findFirstByProductoOrderByIdDesc(Producto producto);

    Set<HistorialCosto> findAllByProductoOrderByIdDesc(Producto producto);
}
