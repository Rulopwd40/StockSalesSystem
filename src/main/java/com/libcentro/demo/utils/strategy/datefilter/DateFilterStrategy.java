package com.libcentro.demo.utils.strategy.datefilter;

import java.util.List;

public interface DateFilterStrategy<T,D> {

    public List<T> filtrar(List<T> datos, String codigo, D[] fechas);
}
