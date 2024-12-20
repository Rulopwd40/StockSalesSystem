package com.libcentro.demo.repository;

import com.libcentro.demo.model.HistorialPrecio;
import com.libcentro.demo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IhistorialpreciosRepository extends JpaRepository<HistorialPrecio, Long> {

    HistorialPrecio findFirstByProductoOrderByIdDesc(Producto producto);

    Set<HistorialPrecio> findByProductoOrderByIdDesc(Producto producto);
}
