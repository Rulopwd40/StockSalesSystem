package com.libcentro.demo.utils.strategy.datefilter;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DateFilterStrategy<T,R,D> {

    public List<T> filtrar( R repository, String codigo, D[] fechas);
}
