package com.libcentro.demo.utils.strategy.count;

import java.util.List;

public interface CountStrategy<T> {
    public String count( List<T> datos, double tiempoEnDias);
}
