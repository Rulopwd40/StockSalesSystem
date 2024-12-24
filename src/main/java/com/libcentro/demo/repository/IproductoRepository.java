package com.libcentro.demo.repository;

import com.libcentro.demo.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.libcentro.demo.model.Producto;

import java.util.List;

@Repository
public interface IproductoRepository extends JpaRepository<Producto, String> {

    List<Producto> findAllByCategoria( Categoria categoria );

    List<Producto> findByStockLessThanEqual(int cantidad);

    @Query("SELECT p FROM Producto p WHERE p.codigobarras LIKE :filter OR p.nombre LIKE :filter OR (p.categoria.nombre LIKE :filter)")
    Page<Producto> getProductosPage (Pageable pageable, String filter);

}
