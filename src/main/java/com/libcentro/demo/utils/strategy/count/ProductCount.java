package com.libcentro.demo.utils.strategy.count;

import com.libcentro.demo.model.Producto;
import com.libcentro.demo.model.Venta_Producto;

import java.util.List;

public class ProductCount implements CountStrategy<Venta_Producto> {

    @Override
    public String count(List<Venta_Producto> datos, double tiempoEnDias) {
        int cantidadVentas = 0;
        int cantidadTotalProducto = 0;
        double totalVentas = 0;
        double gananciaBrutaTotal = 0;
        double gananciaNetaTotal = 0;
        double costoTotal = 0;
        double promedioVentasDia;

        cantidadVentas = datos.size();

        for (Venta_Producto ventaProducto : datos) {
            cantidadTotalProducto += ventaProducto.getCantidad();
            totalVentas += ventaProducto.getTotal();
            gananciaBrutaTotal += ventaProducto.getPrecio_venta() * ventaProducto.getCantidad();
            gananciaNetaTotal += (ventaProducto.getPrecio_venta() - ventaProducto.getDescuento() -ventaProducto.getCosto_compra ()) * ventaProducto.getCantidad();
            costoTotal += ventaProducto.getCosto_compra() ;
        }

        promedioVentasDia = cantidadVentas / tiempoEnDias;

        gananciaBrutaTotal = Math.round(gananciaBrutaTotal * 100.0) / 100.0;
        gananciaNetaTotal = Math.round(gananciaNetaTotal * 100.0) / 100.0;
        costoTotal = Math.round(costoTotal / cantidadVentas * 100.0) / 100.0;
        promedioVentasDia = Math.round(promedioVentasDia * 100.0) / 100.0;

        String pretext = """
                Total ventas de producto | %d
                Cantitad total vendida | %d
                Ganancia Bruta Total | %.2f
                Ganancia Neta Total | %.2f
                Costo Promedio | %.2f
                Ventas/Día | %.2f
                """;

        return String.format(pretext, cantidadVentas,cantidadTotalProducto, gananciaBrutaTotal, gananciaNetaTotal, costoTotal, promedioVentasDia);
    }
}
