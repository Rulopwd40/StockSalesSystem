package com.libcentro.demo.utils.strategy.datefilter;

import com.libcentro.demo.model.Venta;
import com.libcentro.demo.repository.IventaRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public class VentaDateFilter implements DateFilterStrategy<Venta,IventaRepository, LocalDateTime>{

    @Override
    public List<Venta> filtrar ( IventaRepository repository , String codigo, LocalDateTime[] fechas ){
        return repository.findAll().stream().filter (dato -> dato.getFecha ().isAfter (fechas[0]) && dato.getFecha ().isBefore (fechas[1]) && dato.isEstado ()).toList ();
    }

}
