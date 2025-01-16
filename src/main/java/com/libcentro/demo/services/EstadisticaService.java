package com.libcentro.demo.services;

import com.libcentro.demo.model.Venta_Producto;
import com.libcentro.demo.repository.IproductoRepository;
import com.libcentro.demo.repository.IventaRepository;
import com.libcentro.demo.repository.IventaproductoRepository;
import com.libcentro.demo.services.interfaces.IestadisticaService;
import com.libcentro.demo.utils.strategy.count.CountContext;
import com.libcentro.demo.utils.strategy.count.ProductCount;
import com.libcentro.demo.utils.strategy.count.VentaCount;
import com.libcentro.demo.utils.strategy.datefilter.DateFilterContext;
import com.libcentro.demo.utils.strategy.datefilter.ProductoDateFilter;
import com.libcentro.demo.utils.strategy.datefilter.VentaDateFilter;
import com.libcentro.demo.utils.strategy.graph.GraphContext;
import com.libcentro.demo.utils.strategy.graph.ProductGraph;
import com.libcentro.demo.utils.strategy.graph.VentaGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


import javax.imageio.ImageIO;
import java.io.*;
import java.time.*;
import java.awt.*;
import java.util.List;

@Service
public class EstadisticaService implements IestadisticaService {

    private final IventaproductoRepository ventaproductoRepository;
    private final IventaRepository ventaRepository;
    private JpaRepository<?,?> repository;

    private final GraphContext graphContext;
    private final DateFilterContext dateFilterContext;
    private final CountContext countContext;

    @Autowired
    public EstadisticaService( IventaproductoRepository ventaproductoRepository,
                               IventaRepository ventaRepository,
                               GraphContext graphContext,
                               DateFilterContext dateFilterContext,
                               CountContext countContext ) {
        this.ventaproductoRepository = ventaproductoRepository;
        this.ventaRepository = ventaRepository;
        this.graphContext = graphContext;
        this.dateFilterContext = dateFilterContext;
        this.countContext = countContext;
    }


    @Override
    public String contabilizar ( String codigo, String tipo, String tiempo ){
        setearEstrategia (tipo);

        LocalDateTime[] fechas = generarFecha (tiempo);
        if(fechas.length==0) throw new RuntimeException ("Error");

        Duration duration = Duration.between(fechas[0], fechas[1]);
        double duracionEnDias = duration.toHours() / 24.0;

        List<?> datosFiltrados = obtenerYFiltrar (codigo,fechas);

        if( datosFiltrados.isEmpty () ) throw new RuntimeException ("No hay informacion");
        try{
            return countContext.ejecutar (datosFiltrados, duracionEnDias);
        }catch (RuntimeException e){
            throw new RuntimeException (e.getMessage ());
        }
    }

    @Override
    public Image generarGrafica(String codigo, String tipo, String tiempo) {

        setearEstrategia(tipo);

        LocalDateTime[] fechas = generarFecha(tiempo);
        if(fechas.length==0) throw new RuntimeException ("Error");

        List <?> datosFiltrados = obtenerYFiltrar (codigo,fechas);

        try {
            return graphContext.ejecutar (datosFiltrados);
        }catch (RuntimeException e){
            throw new RuntimeException (e.getMessage ());
        }
    }

    private void setearEstrategia ( String tipo ){
        switch (tipo){
            case "venta":
                graphContext.setStrategy (new VentaGraph ());
                repository = ventaRepository;
                dateFilterContext.setStrategy (new VentaDateFilter ());
                countContext.setStrategy (new VentaCount ());
                break;
            case "producto":
                graphContext.setStrategy (new ProductGraph ());
                repository = ventaproductoRepository;
                dateFilterContext.setStrategy (new ProductoDateFilter ());
                countContext.setStrategy (new ProductCount ());
                break;
            default:
                throw new IllegalArgumentException ("Error: Estrategia Errónea");
        }
    }

    private List<?> obtenerYFiltrar(String codigo, LocalDateTime[] fechas){
        return dateFilterContext.ejecutar (repository,codigo,fechas);
    }


    private LocalDateTime[] generarFecha(String tiempo) {
        LocalDateTime fechaFin = LocalDate.now().atTime(LocalTime.MAX);
        LocalDateTime fechaInicio;

        switch (tiempo) {
            case "Hoy":
                fechaInicio = LocalDate.now().atTime(LocalTime.MIN);
                fechaFin = LocalDateTime.now();
                break;
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
