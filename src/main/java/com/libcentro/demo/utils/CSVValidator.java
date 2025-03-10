package com.libcentro.demo.utils;

import com.libcentro.demo.Main;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVValidator {
    private static final Logger logger = LoggerFactory.getLogger(CSVValidator.class);

    public static boolean validarColumnasCSV(String filePath) {
        String[] expectedColumns = {"codigo_barras", "nombre", "categoria", "cantidad", "costo_compra", "precio_unitario"};

        try (CSVReader reader = new CSVReader(new FileReader (filePath))) {
            List<String[]> rows = reader.readAll();

            String[] header = rows.get(0);
            if (header.length == expectedColumns.length) {
                for (int i = 0; i < expectedColumns.length; i++) {
                    if (!header[i].equalsIgnoreCase(expectedColumns[i])) {
                        throw new RuntimeException ("Error: La columna '" + header[i] + "' no es válida.");
                    }
                }
                return true;
            } else {
                throw new RuntimeException ("Error: El número de columnas no coincide con el esperado. " + header.length + " de " + expectedColumns.length);
            }
        } catch (IOException e) {
            logger.error("Error al leer CSV:" + e.getMessage());
            throw new RuntimeException ("Error al leer el archivo CSV: " + e.getMessage());
        } catch (CsvException e) {
            throw new RuntimeException (e);
        }
    }
}
