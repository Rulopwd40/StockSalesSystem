package com.libcentro.demo.utils.strategy.graph;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface GraphStrategy<T> {
    public Image generarGrafica( List<T> datos) throws IOException;
}
