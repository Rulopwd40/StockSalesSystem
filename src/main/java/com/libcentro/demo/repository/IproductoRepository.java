package com.libcentro.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.libcentro.demo.model.producto;

@Repository
public interface IproductoRepository extends JpaRepository<producto, Integer> {
}