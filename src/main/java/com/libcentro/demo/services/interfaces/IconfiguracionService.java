package com.libcentro.demo.services.interfaces;

import java.util.Map;


public interface IconfiguracionService {

    public Map<String,String> obtenerConfiguracion();

    public void actualizarConfiguracion(Map<String,String> configuracion);

}
