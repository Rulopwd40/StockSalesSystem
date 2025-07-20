package com.libcentro.demo.config;

import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Configuration
public class AppConfig {

    private static boolean updated = false;
    public static final boolean PRODUCTION = false;
    public static String python_interpreter;
    public static String python_path;
    public static String csv_path;
    public static String graph_path;
    public static String backup_path;
    public static int stock_alert;
    public static String ticket_path;

    public static final String CONFIG_FILE = "app_config.cfg";

    static {
        loadOrCreateConfig();
    }

    public static void loadOrCreateConfig() {
        Properties properties = new Properties();
        File configFile = new File(CONFIG_FILE);
        if (configFile.exists()) {
            try (FileInputStream inputStream = new FileInputStream(configFile)) {
                properties.load(inputStream);

                // Leer o setear defaults, marcar si hubo actualización
                python_interpreter = getOrSetDefault(properties, "python", PRODUCTION ? "python" : Paths.get("").toAbsolutePath().toString() + File.separator + "python" + File.separator + "generar_grafica.exe");
                python_path = getOrSetDefault(properties, "python_path", PRODUCTION ? "/python/generar_grafica.py" : "");
                csv_path = getOrSetDefault(properties, "csv_path", PRODUCTION ? "csv/" : Paths.get("").toAbsolutePath().toString() + File.separator + "csv" + File.separator);
                graph_path = getOrSetDefault(properties, "graph_path", PRODUCTION ? "graph/" : Paths.get("").toAbsolutePath().toString() + File.separator + "python" + File.separator + "graph");
                backup_path = getOrSetDefault(properties, "backup_path", "backup/");
                ticket_path = getOrSetDefault(properties, "ticket_path", "ticket.json");
                stock_alert = getOrSetInt(properties, "stock_alert", 10);

                if (updated) {
                    try (FileOutputStream outputStream = new FileOutputStream(configFile)) {
                        properties.store(outputStream, "Configuración actualizada automáticamente");
                        System.out.println("Archivo de configuración actualizado con nuevas propiedades faltantes.");
                    } catch (IOException e) {
                        System.err.println("Error al actualizar archivo de configuración: " + e.getMessage());
                    }
                }

                System.out.println("Configuración cargada desde " + CONFIG_FILE);

            } catch (IOException e) {
                System.err.println("Error al leer el archivo de configuración: " + e.getMessage());
            }
        } else {
            python_interpreter = PRODUCTION ? "python" : Paths.get("").toAbsolutePath().toString() + File.separator + "python" + File.separator + "generar_grafica.exe";
            python_path = PRODUCTION ? "/python/generar_grafica.py" : "";
            csv_path = PRODUCTION ? "csv/" : Paths.get("").toAbsolutePath().toString() + File.separator + "csv" + File.separator;
            graph_path = PRODUCTION ? "graph/" : Paths.get("").toAbsolutePath().toString() + File.separator + "python" + File.separator + "graph";
            backup_path = "backup/";
            stock_alert = 10;
            ticket_path = "ticket.json";

            properties.setProperty("python", python_interpreter);
            properties.setProperty("python_path", python_path);
            properties.setProperty("csv_path", csv_path);
            properties.setProperty("graph_path", graph_path);
            properties.setProperty("backup_path", backup_path);
            properties.setProperty("ticket_path", ticket_path);
            properties.setProperty("stock_alert", String.valueOf(stock_alert));

            try (FileOutputStream outputStream = new FileOutputStream(configFile)) {
                properties.store(outputStream, "Configuración de la aplicación");
                System.out.println("Archivo de configuración creado: " + CONFIG_FILE);
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de configuración: " + e.getMessage());
            }
        }
    }
    private static String getOrSetDefault(Properties props, String key, String defaultValue) {
        String value = props.getProperty(key);
        if (value == null) {
            props.setProperty(key, defaultValue);
            updated = true;
            return defaultValue;
        }
        return value;
    }

    private static int getOrSetInt(Properties props, String key, int defaultValue) {
        String value = props.getProperty(key);
        if (value == null) {
            props.setProperty(key, String.valueOf(defaultValue));
            updated = true;
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.err.println("Valor inválido para " + key + ". Se usará valor por defecto: " + defaultValue);
            props.setProperty(key, String.valueOf(defaultValue));
            updated = true;
            return defaultValue;
        }
    }
}