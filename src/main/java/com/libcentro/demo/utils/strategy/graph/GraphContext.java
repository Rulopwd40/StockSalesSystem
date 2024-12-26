package com.libcentro.demo.utils.strategy.graph;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class GraphContext {

    private GraphStrategy strategy;

    public void setStrategy(GraphStrategy strategy) {
        this.strategy = strategy;
    }

    public Image ejecutar( List<?> lista) {
        if (strategy != null) {
            try {
                return strategy.generarGrafica (lista);
            } catch (Exception e) {
                e.printStackTrace ();
            }
        }
        else throw new RuntimeException("No hay estrategias disponibles que resuelvan la gráfica");
        return null;
    }
}
