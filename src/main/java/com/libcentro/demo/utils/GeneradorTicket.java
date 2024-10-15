package com.libcentro.demo.utils;

import com.libcentro.demo.model.ProductoFStock;
import com.libcentro.demo.model.Venta;
import com.libcentro.demo.model.Venta_Producto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneradorTicket {

    private static final String TEXTO_TICKET = "Librería Centro\n" +
            "Fecha: %s\n" +
            "Venta N°: %d\n" +
            "-----------------------------\n" +
            "%s\n" +
            "Total: $%.2f\n";

    public GeneradorTicket() {
    }

    public void generarTicket(Venta venta) {
        StringBuilder productos = new StringBuilder();

        // Productos con stock
        for (Venta_Producto ventaProducto : venta.getListaProductos()) {
            productos.append(ventaProducto.getProducto().getNombre()).append("\n");
            productos.append("$").append(String.format("%.2f", ventaProducto.getPrecio_venta()))
                    .append(" x ").append(ventaProducto.getCantidad());

            if (ventaProducto.getDescuento() > 0) {
                productos.append(" x descuento: ").append(ventaProducto.getDescuento()).append("%");
            }

            productos.append(" = ").append(String.format("%.2f", ventaProducto.getTotal())).append("\n");
        }

        // Productos fuera de stock
        for (ProductoFStock productoFStock : venta.getListaProductosF()) {
            productos.append(productoFStock.getNombre()).append("\n");
            productos.append("$").append(String.format("%.2f", productoFStock.getPrecio_venta()))
                    .append(" x ").append(productoFStock.getCantidad());

            if (productoFStock.getDescuento() > 0) {
                productos.append(" x descuento: ").append(productoFStock.getDescuento()).append("%");
            }

            productos.append(" = ").append(String.format("%.2f", productoFStock.getTotal())).append("\n");
        }

        // Formatear fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fechaFormateada = sdf.format(new Date());

        // Reemplazar variables en el texto
        String ticket = String.format(TEXTO_TICKET, fechaFormateada, venta.getId(), productos.toString(), venta.getTotal());

        System.out.println(ticket);
    }
}