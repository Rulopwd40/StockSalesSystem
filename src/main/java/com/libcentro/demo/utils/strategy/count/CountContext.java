package com.libcentro.demo.utils.strategy.count;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CountContext {

    CountStrategy strategy;

    public void setStrategy ( CountStrategy strategy ){
        this.strategy = strategy;
    }

    public String ejecutar( List<?> datos, double tiempoEnDias) {
        return strategy.count (datos, tiempoEnDias);
    }
}
