package com.libcentro.demo.utils.strategy.datefilter;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DateFilterContext {

    private DateFilterStrategy strategy;

    public void setStrategy(DateFilterStrategy strategy) {
        this.strategy = strategy;
    }

    public List<?> ejecutar( List<?> datos ,String codigo, Object[] fechas ){
        if(!datos.isEmpty ()) return this.strategy.filtrar(datos, codigo, fechas);
        else throw new RuntimeException("Datos no encontrados");
    }

}
