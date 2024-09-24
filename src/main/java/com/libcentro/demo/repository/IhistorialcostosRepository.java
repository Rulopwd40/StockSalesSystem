package com.libcentro.demo.repository;

import com.libcentro.demo.model.HistorialCosto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IhistorialcostosRepository extends JpaRepository<HistorialCosto,Long> {
}
