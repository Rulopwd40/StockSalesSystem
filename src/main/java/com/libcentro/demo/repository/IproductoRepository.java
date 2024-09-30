package com.libcentro.demo.repository;

import com.libcentro.demo.model.Categoria;
import com.libcentro.demo.model.HistorialCosto;
import com.libcentro.demo.model.HistorialPrecio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.libcentro.demo.model.Producto;

import java.util.List;
import java.util.Set;

@Repository
public interface IproductoRepository extends JpaRepository<Producto, String> {

    @Query("SELECT p FROM Producto p LEFT JOIN FETCH p.historial_costos hCosto LEFT JOIN FETCH p.historial_precios hPrecio WHERE p.codigo_barras = :codigoBarras")
    Producto findByCodigoBarrasWithHistorial(@Param("codigoBarras") String codigoBarras);

    @Query("SELECT p FROM Producto p WHERE p.codigo_barras = :codigoBarras")
    Producto findByCodigoBarras(@Param("codigoBarras") String codigoBarras);

    @Query("SELECT p FROM Producto p WHERE p.nombre = :nombre")
    Producto findByNombre(@Param("nombre") String nombre);

    @Modifying
    @Query("UPDATE Producto p SET p.precio_venta = ROUND(p.precio_venta + (p.precio_venta * :porcentaje),2) WHERE p.categoria = :categoria ")
    int updateProductoPrecioByCategoria(@Param("categoria") Categoria categoria, @Param("porcentaje") float porcentaje);

    @Modifying
    @Query("UPDATE Producto p SET p.precio_venta = ROUND(p.precio_venta+ (p.precio_venta * :porcentaje),2) WHERE true")
    int updateProductoGeneral(@Param("porcentaje")float porcentaje);


    @Query("SELECT p FROM Producto p WHERE p.categoria = :categoria")
    Set<Producto> findByCategoria(@Param("categoria") Categoria categoria);

}