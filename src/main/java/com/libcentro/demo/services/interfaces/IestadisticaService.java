package com.libcentro.demo.services.interfaces;

import com.libcentro.demo.model.Producto;

import java.awt.*;

public interface IestadisticaService {

    Image generarGrafica(String codigo, String tipo, String tiempo);


}
