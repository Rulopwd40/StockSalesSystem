package com.libcentro.demo.services;

import com.libcentro.demo.config.AppConfig;
import com.libcentro.demo.services.interfaces.IconfiguracionService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class ConfiguracionService implements IconfiguracionService {



    @Override
    public Map<String, String> obtenerConfiguracion() {
        Properties prop = new Properties();
        File config = new File(AppConfig.CONFIG_FILE);
        Map<String, String> configuracion = new HashMap<> ();
        if (config.exists()) {
            try (FileReader reader = new FileReader(config)) {
                prop.load(reader);

                for (String key : prop.stringPropertyNames()) {
                    configuracion.put(key, prop.getProperty(key));
                }

            } catch (IOException e) {
                System.err.println("Error al leer el archivo de configuración: " + e.getMessage());
            }
        } else {
            System.out.println("El archivo de configuración no existe.");
        }

        return configuracion;
    }

    @Override
    public void actualizarConfiguracion(Map<String, String> configuracion) {
        Properties prop = new Properties();
        File config = new File(AppConfig.CONFIG_FILE);

        try {
            if (config.exists()) {
                try (FileReader reader = new FileReader(config)) {
                    prop.load(reader);
                }
            }

            for (Map.Entry<String, String> entry : configuracion.entrySet()) {
                prop.setProperty(entry.getKey(), entry.getValue());
            }

            try (FileWriter writer = new FileWriter (config)) {
                prop.store(writer, "Archivo de Configuración Actualizado");
            }

            AppConfig.loadOrCreateConfig ();
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de configuracion: " + e.getMessage());
        }

    }
}
