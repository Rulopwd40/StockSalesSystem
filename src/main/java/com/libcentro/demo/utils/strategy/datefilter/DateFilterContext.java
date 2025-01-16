package com.libcentro.demo.utils.strategy.datefilter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DateFilterContext {

    private DateFilterStrategy strategy;

    public void setStrategy(DateFilterStrategy strategy) {
        this.strategy = strategy;
    }

    public List<?> ejecutar( JpaRepository<?,?> repository, String codigo, Object[] fechas ){
        return this.strategy.filtrar(repository, codigo, fechas);

    }

}
