package com.libcentro.demo.services.interfaces;

import com.libcentro.demo.model.Producto;

import java.awt.*;

public interface IestadisticaService {


    String contabilizar(String codigo, String tipo, String tiempo);

    Image generarGrafica(String codigo, String tipo, String tiempo);


}
