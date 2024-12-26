package com.libcentro.demo.utils.strategy.graph;

import com.libcentro.demo.config.AppConfig;
import com.libcentro.demo.model.Venta;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VentaGraph implements GraphStrategy<Venta> {
    @Override
    public Image generarGrafica ( List<Venta> datos ){
        try {
            FileWriter writer = new FileWriter (AppConfig.csv_path + "venta_data.csv");
            writer.write ("Fecha,GananciaNeta\n");

            Map<String, Double> gananciasPorFecha = new HashMap<> ();

            for (Venta venta : datos) {
                String fechaVenta = String.valueOf (venta.getFecha ());

                double ganancia = venta.getTotal ();

                gananciasPorFecha.put (fechaVenta, gananciasPorFecha.getOrDefault (fechaVenta, 0d) + ganancia);
            }

            for (Map.Entry<String, Double> entry : gananciasPorFecha.entrySet ()) {
                writer.write (entry.getKey () + "," + entry.getValue () + "\n");
            }

            writer.close ();
        } catch (IOException e) {
            throw new RuntimeException ("Error al escribir el archivo CSV", e);
        }

        try {
            ProcessBuilder processBuilder = new ProcessBuilder ("python", AppConfig.python_path, AppConfig.csv_path + "venta_data.csv", "venta");

            processBuilder.redirectErrorStream (true);
            Process process = processBuilder.start ();

            StringBuilder output = new StringBuilder ();
            BufferedReader reader = new BufferedReader (new InputStreamReader (process.getInputStream ()));
            String line;
            while ((line = reader.readLine ()) != null) {
                output.append (line).append ("\n");
            }

            int exitCode = process.waitFor ();
            if ( exitCode != 0 ) {
                throw new RuntimeException ("Error en el script Python. Código de salida: " + exitCode + "\n" + output.toString ());
            }

            File file = new File (AppConfig.graph_path + "grafica.png");
            Image image = ImageIO.read (file);

            return image;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException ("Error al ejecutar el script Python", e);
        }


    }

    private List<Venta> filtrarVentasFecha ( List<Venta> datos, LocalDateTime fechaInicio, LocalDateTime fechaFin ){
        return datos.stream().filter(dato -> dato.getFecha ().isAfter (fechaInicio) && dato.getFecha ().isBefore (fechaFin)).toList ();
    }
}
