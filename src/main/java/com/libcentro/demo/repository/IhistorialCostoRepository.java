package com.libcentro.demo.repository;


import com.libcentro.demo.model.HistorialCosto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IhistorialCostoRepository extends JpaRepository<HistorialCosto, Long> {


}
