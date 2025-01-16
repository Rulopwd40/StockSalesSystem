package com.libcentro.demo.utils.strategy.graph;

import com.libcentro.demo.config.AppConfig;
import com.libcentro.demo.model.Venta_Producto;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ProductGraph implements GraphStrategy<Venta_Producto> {

    @Override
    public Image generarGrafica(List<Venta_Producto> datos) {
        try {
            // Crear archivo CSV
            FileWriter writer = new FileWriter (AppConfig.csv_path + "venta_producto_data.csv");
            writer.write("Fecha,GananciaBruta,GananciaNeta,Costo\n");

            Map<String, double[]> dataPorFecha = new HashMap<> ();

            for (Venta_Producto vp : datos) {
                String fechaVenta = vp.getVenta().getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                double costo = vp.getCosto_compra() * vp.getCantidad();
                double gananciaBruta = vp.getPrecio_venta() * vp.getCantidad();
                double descuento = vp.getDescuento();
                double gananciaNeta = gananciaBruta - descuento - costo;

                dataPorFecha.putIfAbsent(fechaVenta, new double[]{0, 0, 0});
                double[] valores = dataPorFecha.get(fechaVenta);
                valores[0] += gananciaBruta;
                valores[1] += gananciaNeta;
                valores[2] += costo;
                dataPorFecha.put(fechaVenta, valores);
            }

            for (Map.Entry<String, double[]> entry : dataPorFecha.entrySet()) {
                String fecha = entry.getKey();
                double[] valores = entry.getValue();
                writer.write(String.format(Locale.US, "%s,%.2f,%.2f,%.2f\n", fecha, valores[0], valores[1], valores[2]));
            }

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir el archivo CSV", e);
        }

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "python",
                    AppConfig.python_path,
                    AppConfig.csv_path + "venta_producto_data.csv",
                    "producto"
            );

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader (process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException(
                        "Error en el script Python. Código de salida: " + exitCode + "\n" + output
                );
            }

            File file = new File(AppConfig.graph_path + "grafica.png");
            return ImageIO.read(file);

        } catch (IOException e) {
            throw new RuntimeException("Error al escribir/leer el archivo");
        }  catch (InterruptedException e) {
            throw new RuntimeException ("Procesamiento interrumpido");
        }
    }
}
