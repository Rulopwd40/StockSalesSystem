package com.libcentro.demo.utils.strategy.count;

import com.libcentro.demo.model.Venta;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class VentaCount implements CountStrategy<Venta> {
    @Override
    public String count ( List<Venta> datos , double tiempoEnDias){
        int cantidad = 0;
        double promedio_diario;
        LocalDateTime mayor_fecha;
        double monto_maximo;
        double total=0;
        double total_diario_promedio;

        cantidad = datos.size ();
        promedio_diario = cantidad/tiempoEnDias;
        Venta ventamax = datos.stream()
                .max(Comparator.comparing(Venta::getTotal))
                .orElse(null);
        mayor_fecha = ventamax.getFecha();
        monto_maximo = ventamax.getTotal ();
        total = datos.stream()
                .mapToDouble(Venta::getTotal)
                .sum();
        total_diario_promedio = total/tiempoEnDias;

        BigDecimal bdMontoMaximo = BigDecimal.valueOf (monto_maximo).setScale(2, RoundingMode.HALF_UP);
        monto_maximo = bdMontoMaximo.doubleValue();

        total = datos.stream()
                .mapToDouble(Venta::getTotal)
                .sum();

        BigDecimal bdTotal = BigDecimal.valueOf (total).setScale(2, RoundingMode.HALF_UP);
        total = bdTotal.doubleValue();

        BigDecimal bdPromedioDiario = BigDecimal.valueOf (promedio_diario).setScale(2, RoundingMode.HALF_UP);
        promedio_diario = bdPromedioDiario.doubleValue();

        total_diario_promedio = total / tiempoEnDias;

        BigDecimal bdTotalDiarioPromedio = BigDecimal.valueOf(total_diario_promedio).setScale(2, RoundingMode.HALF_UP);
        total_diario_promedio = bdTotalDiarioPromedio.doubleValue();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = mayor_fecha.format(formatter);

        String pretext = """
                Total ventas | %d
                Ventas/Dia | %.2f
                Mayor venta | %.2f
                Fecha de mayor venta | %s
                Ganancia total | %.2f
                Ganancia promedio | %.2f
                """;

        return String.format(pretext,cantidad,promedio_diario,monto_maximo,fechaFormateada,total,total_diario_promedio);
    }
}
