package com.libcentro.demo.repository;

import com.libcentro.demo.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.libcentro.demo.model.Producto;

import java.util.List;

@Repository
public interface IproductoRepository extends JpaRepository<Producto, String> {

    List<Producto> findAllByCategoria( Categoria categoria );


    List<Producto> findByStockLessThanEqual(int cantidad);

    @Query("SELECT p FROM Producto p " +
            "LEFT JOIN p.categoria c " +
            "WHERE (:filter = '' OR LOWER(p.codigobarras) LIKE LOWER(:filter) " +
            "OR LOWER(p.nombre) LIKE LOWER(:filter) OR LOWER(c.nombre) LIKE LOWER(:filter)) " +
            "AND (:sin_stock = false OR p.stock = 0) " +
            "AND (:conCategoriaNula = false OR c IS NULL) " +
            "ORDER BY CAST(p.codigobarras AS BIGINTEGER) ASC")
    Page<Producto> getProductosPage(Pageable pageable, @Param("filter") String filter,
                                    @Param("sin_stock") boolean sin_stock, @Param("conCategoriaNula") boolean conCategoriaNula);

    @Modifying
    @Query("UPDATE Producto p SET p.categoria = NULL WHERE p.categoria.id = :categoriaId")
    void setCategoriaNull(@Param("categoriaId") Long categoriaId);

}
