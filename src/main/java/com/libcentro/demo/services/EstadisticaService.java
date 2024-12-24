package com.libcentro.demo.services;

import com.libcentro.demo.model.Venta;
import com.libcentro.demo.model.Venta_Producto;
import com.libcentro.demo.repository.IventaRepository;
import com.libcentro.demo.repository.IventaproductoRepository;
import com.libcentro.demo.services.interfaces.IestadisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.imageio.ImageIO;
import java.io.*;
import java.time.*;
import java.util.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadisticaService implements IestadisticaService {

    @Autowired
    IventaproductoRepository ventaproductoRepository;

    @Autowired
    IventaRepository ventaRepository;

    static String python_path = "src/main/python/generar_grafica.py";

    static String csv_path = "csv/";
    static String graph_path = "graph/";

    @Override
    public Image generarGrafica(String codigo, String tipo, String tiempo) {

        LocalDateTime[] fechas = generarFecha(tiempo);

        if(fechas.length==0) throw new RuntimeException ("Error");

        LocalDateTime fechaInicio = fechas[0];
        LocalDateTime fechaFin = fechas[1];

      /*  if (tipo == "producto") {
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
        } else*/ if (tipo.equals ("venta")) {
            List<Venta> ventas;
            try {
                List<Venta> ventasAll = ventaRepository.findAll();
                ventas = ventasAll.stream()
                        .filter(venta -> venta.getFecha().isAfter(fechaInicio) && venta.getFecha().isBefore(fechaFin))
                        .collect(Collectors.toList());
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
            if(ventas.isEmpty ()){
                throw new RuntimeException("No hay informacion de ventas");
            }

            return generarGraficaVenta(ventas, fechaInicio, fechaFin);
        }

        return null;
    }

    private Image generarGraficaProducto( List<Venta_Producto> ventaproductos, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
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



    public Image generarGraficaVenta( List<Venta> ventas, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        try {
            FileWriter writer = new FileWriter(csv_path + "venta_data.csv");
            writer.write("Fecha,GananciaNeta\n");

            Map<String, Double> gananciasPorFecha = new HashMap<>();

            for (Venta venta : ventas) {
                String fechaVenta = String.valueOf (venta.getFecha());

                double ganancia = venta.getTotal ();

                gananciasPorFecha.put(fechaVenta, gananciasPorFecha.getOrDefault(fechaVenta, 0d) + ganancia);
            }

            for (Map.Entry<String, Double> entry : gananciasPorFecha.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir el archivo CSV", e);
        }

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", python_path, csv_path + "venta_data.csv", "venta");

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

            File file = new File(graph_path +"grafica.png");
            Image image = ImageIO.read(file);

            return image;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al ejecutar el script Python", e);
        }
    }



    public LocalDateTime[] generarFecha(String tiempo) {
        LocalDateTime fechaFin = LocalDate.now().atTime(LocalTime.MAX);
        LocalDateTime fechaInicio;

        switch (tiempo) {
            case "Ayer":
                fechaInicio = fechaFin.minusDays(1).with(LocalTime.MIN);
                fechaFin = fechaFin.minusDays(1);
                break;
            case "Ultima semana":
                fechaInicio = fechaFin.minusWeeks(1).with(DayOfWeek.MONDAY).with(LocalTime.MIN);
                break;
            case "Ultimo mes":
                fechaInicio = fechaFin.minusMonths(1).withDayOfMonth(1).with(LocalTime.MIN);
                break;
            case "Ultimo trimestre":
                fechaInicio = fechaFin.minusMonths(3).withMonth(1).withDayOfMonth(1).with(LocalTime.MIN);
                break;
            case "Ultimo semestre":
                fechaInicio = fechaFin.minusMonths(6).withMonth(1).withDayOfMonth(1).with(LocalTime.MIN);
                break;
            case "Ultimo año":
                fechaInicio = fechaFin.minusYears(1).withDayOfYear(1).with(LocalTime.MIN);
                break;
            case "Todos los tiempos":
                fechaInicio = LocalDate.of(2000, 1, 1).atStartOfDay();
                break;
            default:
                throw new IllegalArgumentException("Opción de tiempo no válida: " + tiempo);
        }

        return new LocalDateTime[]{fechaInicio, fechaFin};
    }


}
