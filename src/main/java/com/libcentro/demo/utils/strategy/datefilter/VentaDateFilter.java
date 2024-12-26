package com.libcentro.demo.utils.strategy.datefilter;

import com.libcentro.demo.model.Venta;

import java.time.LocalDateTime;
import java.util.List;

public class VentaDateFilter implements DateFilterStrategy<Venta, LocalDateTime>{
    @Override
    public List<Venta> filtrar ( List<Venta> datos,String codigo, LocalDateTime[] fechas ){
        return datos.stream().filter (dato -> dato.getFecha ().isAfter (fechas[0]) && dato.getFecha ().isBefore (fechas[1])).toList ();
    }
}
