package com.libcentro.demo.config;

import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.Properties;

@Configuration
public class AppConfig {

    public static String python_path;
    public static String csv_path;
    public static String graph_path;
    public static String backup_path;
    public static int stock_alert;

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

                python_path = properties.getProperty("python_path", "src/main/python/generar_grafica.py");
                csv_path = properties.getProperty("csv_path", "csv/");
                graph_path = properties.getProperty("graph_path", "graph/");
                backup_path = properties.getProperty("backup_path", "backup/");

                String stockAlertValue = properties.getProperty("stock_alert", "10");
                try {
                    stock_alert = Integer.parseInt(stockAlertValue);
                } catch (NumberFormatException e) {
                    System.err.println("Valor inválido para stock_alert en el archivo de configuración. Usando el valor por defecto: 10");
                    stock_alert = 10;
                }

                System.out.println("Configuración cargada desde " + CONFIG_FILE);

            } catch (IOException e) {
                System.err.println("Error al leer el archivo de configuración: " + e.getMessage());
            }
        } else {
            python_path = "src/main/python/generar_grafica.py";
            csv_path = "csv/";
            graph_path = "graph/";
            backup_path = "backup/";
            stock_alert = 10;

            properties.setProperty("python_path", python_path);
            properties.setProperty("csv_path", csv_path);
            properties.setProperty("graph_path", graph_path);
            properties.setProperty("stock_alert", String.valueOf(stock_alert));
            properties.setProperty("backup_path", backup_path);

            try (FileOutputStream outputStream = new FileOutputStream(configFile)) {
                properties.store(outputStream, "Configuración de la aplicación");
                System.out.println("Archivo de configuración creado: " + CONFIG_FILE);
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de configuración: " + e.getMessage());
            }
        }
    }
}