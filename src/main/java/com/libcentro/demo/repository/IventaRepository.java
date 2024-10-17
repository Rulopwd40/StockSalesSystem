package com.libcentro.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.libcentro.demo.model.Venta;

@Repository
public interface IventaRepository extends JpaRepository<Venta, Integer> {

}
