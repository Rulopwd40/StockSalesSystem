package com.libcentro.demo.config;

import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

import static com.libcentro.demo.config.AppConfig.PRODUCTION;

@Configuration
public class TicketConfig {

    public static int ancho;
    public static String ticketPath;


    public static final String CONFIG_FILE = "ticket_config.cfg";

    static {
        loadOrCreateConfig();
    }


    public static void loadOrCreateConfig() {
        Properties properties = new Properties();
        File configFile = new File(CONFIG_FILE);

        if (configFile.exists()) {
            try (FileInputStream inputStream = new FileInputStream(configFile)) {
                properties.load(inputStream);
                try {
                ancho = Integer.parseInt (properties.getProperty("ancho", "58"));
                } catch (NumberFormatException e) {
                    System.err.println("Valor inválido para ancho en el archivo de configuración. Usando el valor por defecto: 10");
                }

                ticketPath = properties.getProperty("backup_path", "");
                System.out.println("Configuración cargada desde " + CONFIG_FILE);

            } catch (IOException e) {
                System.err.println("Error al leer el archivo de configuración: " + e.getMessage());
            }
        } else {
            ancho = 58;
            ticketPath = PRODUCTION? "ticket" : Paths.get ("").toAbsolutePath().toString() + File.separator + "ticket" + File.separator;

            properties.setProperty("ancho", String.valueOf (ancho));
            properties.setProperty("ticket", ticketPath);

            try (FileOutputStream outputStream = new FileOutputStream(configFile)) {
                properties.store(outputStream, "Configuración de la aplicación");
                System.out.println("Archivo de configuración creado: " + CONFIG_FILE);
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de configuración: " + e.getMessage());
            }
        }
    }
}
