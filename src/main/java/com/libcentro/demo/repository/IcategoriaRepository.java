package com.libcentro.demo.repository;

import com.libcentro.demo.model.Categoria;
import com.libcentro.demo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IcategoriaRepository extends JpaRepository<Categoria, String> {

    @Query("SELECT c FROM Categoria c WHERE c.nombre = :nombre")
    Categoria findByNombre(@Param("nombre") String codigoBarras);
}
