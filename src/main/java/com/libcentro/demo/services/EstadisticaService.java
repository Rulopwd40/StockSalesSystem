package com.libcentro.demo.services;

import com.libcentro.demo.model.Producto;
import com.libcentro.demo.model.Venta;
import com.libcentro.demo.model.Venta_Producto;
import com.libcentro.demo.repository.IventaproductoRepository;
import com.libcentro.demo.services.interfaces.IestadisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EstadisticaService implements IestadisticaService {

    @Autowired
    IventaproductoRepository ventaproductoRepository;

    @Override
    public Image generarGrafica(String codigo, String tipo, String tiempo) {
        String fecha = generarFecha(tiempo);

        String[] fechas = fecha.split(",");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date fechaInicio;
        Date fechaFin;
        try {
            // Parsear la fecha de inicio (primera fecha del array)
            fechaInicio = sdf.parse(fechas[0]);
            fechaFin = sdf.parse(fechas[1]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (tipo == "producto") {
            List<Venta_Producto> ventaproductos;
            try {
                ventaproductos = ventaproductoRepository.findByCodigo_barrasAndVentaFechaBetween (codigo, fechaInicio, fechaFin);
            }catch (Exception e) {
                throw new RuntimeException(e);
            }

            if(ventaproductos.isEmpty()) {
                throw new RuntimeException("No se encontro inforación del producto");
            }
            return generarGraficaProducto(ventaproductos, fechaInicio, fechaFin);
        } else if (tipo == "venta") {
            return generarGraficaVenta(ventaproductos, fechaInicio, fechaFin);
        }

        return null;

    }

    private Image generarGraficaProducto(ArrayList<Venta_Producto> ventaproductos, Date fechaInicio, Date fechaFin) {
        try {
            FileWriter writer = new FileWriter("producto_data.csv");
            writer.write("Fecha,GananciaNeta\n");

            for (Venta_Producto vp : ventaproductos) {
                String fechaVenta = vp.getVenta().getFecha().toString(); // Asegúrate de obtener la fecha correctamente

                double gananciaNeta = (vp.getPrecio_venta() - vp.getCosto_compra()) * vp.getCantidad();

                writer.write(fechaVenta + "," + gananciaNeta + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir el archivo CSV", e);
        }

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", "generar_grafica.py", "producto_data.csv", "producto");

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Error en el script Python. Código de salida: " + exitCode + "\n" + output.toString());
            }

            File file = new File("grafica.png");
            Image image = ImageIO.read(file);

            return image;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al ejecutar el script Python", e);
        }
    }



    public Image generarGraficaVenta( ArrayList<Venta> ventaproductos, Date fechaInicio, Date fechaFin) {
        // Generar CSV con los datos (ventas y ganancia neta)
        try {
            FileWriter writer = new FileWriter("venta_data.csv");
            writer.write("Fecha,GananciaNeta\n");

            // Agrupar las ventas por fecha (o por cualquier otro criterio que necesites)
            Map<String, Double> gananciasPorFecha = new HashMap<>();

            for (Venta_Producto vp : ventaproductos) {
                // Obtener la fecha de la venta (puedes cambiar la forma en que la formateas si es necesario)
                String fechaVenta = vp.getVenta().getFecha(); // Asegúrate de obtener la fecha correctamente

                // Calcular la ganancia neta: (precio_venta - costo_compra) * cantidad
                double gananciaNeta = (vp.getPrecio_venta() - vp.getCosto_compra()) * vp.getCantidad();

                // Agrupar las ganancias por fecha
                gananciasPorFecha.put(fechaVenta, gananciasPorFecha.getOrDefault(fechaVenta, 0d) + gananciaNeta);
            }

            // Escribir las ganancias agrupadas por fecha en el archivo CSV
            for (Map.Entry<String, Double> entry : gananciasPorFecha.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir el archivo CSV", e);
        }

        // Ejecutar el script Python con ProcessBuilder
        try {
            // Configura el comando para ejecutar el script de Python
            ProcessBuilder processBuilder = new ProcessBuilder("python", "generar_grafica.py", "venta_data.csv", "venta");

            // Redirigir la salida de error y la salida estándar
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Capturar la salida del proceso
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Esperar a que el proceso termine y verificar el código de salida
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Error en el script Python. Código de salida: " + exitCode + "\n" + output.toString());
            }

            // Leer la imagen generada por el script Python
            File file = new File("grafica.png");
            Image image = ImageIO.read(file);

            return image;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al ejecutar el script Python", e);
        }

    }



    public String generarFecha(String tiempo) {
        LocalDateTime fechaFin = LocalDate.now().atStartOfDay();
        LocalDateTime fechaInicio;

        switch (tiempo) {
            case "Ayer":
                fechaInicio = fechaFin.minusDays(1);
                fechaFin = fechaInicio;
                break;
            case "Ultima semana":
                fechaInicio = fechaFin.minusWeeks(1);
                break;
            case "Ultimo mes":
                fechaInicio = fechaFin.minusMonths(1);
                break;
            case "Ultimo trimestre":
                fechaInicio = fechaFin.minusMonths(3);
                break;
            case "Ultimo semestre":
                fechaInicio = fechaFin.minusMonths(6);
                break;
            case "Ultimo año":
                fechaInicio = fechaFin.minusYears(1);
            case "Todos los tiempos":
                // Asumimos una fecha de inicio arbitraria para "Todos los tiempos"
                fechaInicio = LocalDate.of(2000, 1, 1).atStartOfDay();
                break;
            case "Elegir período":
                // Lógica adicional para permitir seleccionar un rango personalizado
                return "Seleccionar período personalizado";
            default:
                throw new IllegalArgumentException("Opción de tiempo no válida: " + tiempo);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return fechaInicio.format(formatter) + "," + fechaFin.format(formatter);
    }


}
