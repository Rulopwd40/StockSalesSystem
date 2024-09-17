package com.libcentro.demo.repository;

import com.libcentro.demo.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IcategoriaRepository extends JpaRepository<Categoria, String> {
}
