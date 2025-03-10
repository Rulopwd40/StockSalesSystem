package com.libcentro.demo.utils.strategy.graph;

import com.libcentro.demo.Main;
import com.libcentro.demo.config.AppConfig;
import com.libcentro.demo.model.Venta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VentaGraph implements GraphStrategy<Venta> {

    private final Logger logger = LoggerFactory.getLogger(VentaGraph.class);

    @Override
    public Image generarGrafica ( List<Venta> datos ){
        if(datos.isEmpty ()) return null;
        try {
            logger.info ("Abriendo archivo: {}",AppConfig.csv_path);
            FileWriter writer = new FileWriter (AppConfig.csv_path + "/venta_data.csv");
            writer.write ("Fecha,Ganancia\n");
            logger.info ("Procesando datos...");

            Map<String, Double> gananciasPorFecha = new HashMap<> ();

            for (Venta venta : datos) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                String fechaVenta = venta.getFecha ().format(formatter);

                double ganancia = venta.getTotal ();

                gananciasPorFecha.put (fechaVenta, gananciasPorFecha.getOrDefault (fechaVenta, 0d) + ganancia);
            }

            for (Map.Entry<String, Double> entry : gananciasPorFecha.entrySet ()) {
                writer.write (entry.getKey () + "," + entry.getValue () + "\n");
            }

            writer.close ();
            logger.info ("Archivo creado...");
        } catch (IOException e) {
            throw new RuntimeException ("Error al escribir el archivo CSV", e);
        }

        try {
            logger.info ("Estableciendo conexion con el generador: {}, {}",AppConfig.python_interpreter,AppConfig.python_path);

            ProcessBuilder processBuilder;
            if( AppConfig.PRODUCTION) processBuilder = new ProcessBuilder (AppConfig.python_interpreter, AppConfig.python_path, AppConfig.csv_path + "venta_data.csv", "venta");
            else processBuilder = new ProcessBuilder (AppConfig.python_interpreter, AppConfig.csv_path + "venta_data.csv", "venta");
            processBuilder.redirectErrorStream (true);
            logger.info ("Generando imagen...");
            Process process = processBuilder.start ();

            StringBuilder output = new StringBuilder ();
            BufferedReader reader = new BufferedReader (new InputStreamReader (process.getInputStream ()));
            String line;
            while ((line = reader.readLine ()) != null) {
                output.append (line).append ("\n");
            }

            int exitCode = process.waitFor ();
            if ( exitCode != 0 ) {
                logger.error ("Error en el script Python. Código de salida: {}\n{}", exitCode, output.toString ());
                throw new RuntimeException ("Error en el script Python. Código de salida: " + exitCode + "\n" + output.toString ());
            }

            File file = new File (AppConfig.graph_path + "/grafica.png");
            logger.info (file.getAbsolutePath ());
            Image image = ImageIO.read (file);
            if(image!= null) logger.info ("Imagen generada en {}\n{}", file.getAbsolutePath (), image);
            else logger.error("Imagen no generada");
            return image;

        }   catch (IOException e) {
            throw new RuntimeException("Error al escribir/leer el archivo");
        }  catch (InterruptedException e) {
            throw new RuntimeException ("Procesamiento interrumpido");
        }


    }

    private List<Venta> filtrarVentasFecha ( List<Venta> datos, LocalDateTime fechaInicio, LocalDateTime fechaFin ){
        return datos.stream().filter(dato -> dato.getFecha ().isAfter (fechaInicio) && dato.getFecha ().isBefore (fechaFin)).toList ();
    }
}
