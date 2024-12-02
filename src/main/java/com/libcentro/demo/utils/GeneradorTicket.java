package com.libcentro.demo.utils;

import com.libcentro.demo.model.ProductoFStock;
import com.libcentro.demo.model.Venta;
import com.libcentro.demo.model.Venta_Producto;

import java.awt.*;
import java.awt.print.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneradorTicket implements Printable {

    private static final int ANCHO_LINEA = 40;  // Número de caracteres por línea (aproximado)
    private static final String SEPARADOR = "-".repeat(ANCHO_LINEA);
    private static final int ANCHO_TICKET = 55;
    // Texto del ticket con guiones dinámicos para ajustar el ancho del papel
    private static final String TEXTO_TICKET = "Libreria Centro\n" +
            "Fecha: %s\n" +
            "Venta N°: %d\n" +
            "-----------------------------\n" +  // Línea de guiones ajustada
            "%s\n" +  // Aquí se colocarán los productos
            "-----------------------------\n" +  // Línea de guiones ajustada
            "Total: %s\n";  // Asegúrate de que el total se pase como String

    String ticket;

    public GeneradorTicket() {
    }

    public String generarTicket(Venta venta) {
        StringBuilder productos = new StringBuilder();

        // Productos en la venta
        for (Venta_Producto ventaProducto : venta.getListaProductos()) {
            String nombre = ventaProducto.getProducto().getNombre().toUpperCase();
            String precio = String.format("$%.2f", ventaProducto.getProducto().getPrecio_venta());
            String cantidad = String.valueOf(ventaProducto.getCantidad());
            String descuento = (ventaProducto.getDescuento() > 0)
                    ? String.format("- $%.2f", (ventaProducto.getProducto().getPrecio_venta() * ventaProducto.getCantidad() * ventaProducto.getDescuento() / 100))
                    : "";
            String porcentaje = (ventaProducto.getDescuento() > 0) ? String.format("%.2f",ventaProducto.getDescuento()) : "";
            String total = String.format("= $%.2f", ventaProducto.getTotal());

            // Imprimir el nombre del producto
            productos.append(String.format("%-30s\n", nombre));
            // Imprimir precio y cantidad en la siguiente línea
            productos.append(String.format("%-5s x %-2s\n", precio, cantidad));

            // Imprimir descuento en una nueva línea, alineado a la derecha
            if (!descuento.isEmpty()) {
                productos.append(String.format("    %s(%s%%)\n", descuento,porcentaje));
            }

            // Alinear el total a la derecha
            productos.append(String.format("%" + ANCHO_TICKET + "s\n", total));
        }
        for (ProductoFStock productoF : venta.getListaProductosF()) {
            String nombreF = productoF.getNombre().toUpperCase(); // Asumiendo que ProductoFStock tiene un método getNombre()
            String precioF = String.format("$%.2f", productoF.getPrecio_venta());
            String cantidadF = String.valueOf(productoF.getCantidad());
            String descuentoF = (productoF.getDescuento() > 0)
                    ? String.format("- $%.2f", (productoF.getPrecio_venta() * productoF.getCantidad() * productoF.getDescuento() / 100))
                    : "";
            String porcentajeF = (productoF.getDescuento() > 0) ? String.format("%.2f", productoF.getDescuento()) : "";
            String totalF = String.format("= $%.2f", productoF.getPrecio_venta() * productoF.getCantidad() - (productoF.getPrecio_venta() * productoF.getCantidad() * productoF.getDescuento() / 100));

            // Imprimir el nombre del producto
            productos.append(String.format("%-30s\n", nombreF));
            // Imprimir precio y cantidad en la siguiente línea
            productos.append(String.format("%-5s x %-2s\n", precioF, cantidadF));

            // Imprimir descuento en una nueva línea, alineado a la derecha
            if (!descuentoF.isEmpty()) {
                productos.append(String.format("    %s(%s%%)\n", descuentoF, porcentajeF));
            }

            // Alinear el total a la derecha
            productos.append(String.format("%" + ANCHO_TICKET + "s\n", totalF));
        }

        // Calcular el total final y formatearlo
        double totalVenta = venta.getTotal(); // Obtener el total como float
        String totalFormateado = String.format("$%.2f", totalVenta); // Formatear el total

        // Formatear fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fechaFormateada = sdf.format(new Date());

        // Reemplazar variables en el texto
        ticket = String.format(TEXTO_TICKET, fechaFormateada, venta.getId(), productos.toString(), totalFormateado);

        // Mostrar el ticket en la consola
        System.out.println(ticket);
        return ticket;
    }

    public void imprimirTicket() {
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = job.defaultPage();
        Paper paper = new Paper();

        // Tamaño de papel 56 mm de ancho y altura variable
        double width = 56 * 2.83;  // 56 mm de ancho en puntos (aproximadamente 158.48 puntos)
        double height = 10000; // Altura variable, ajusta a un valor grande para el contenido variable
        paper.setSize(width, height);

        // Definir el área imprimible para que coincida con el tamaño total del papel
        paper.setImageableArea(0, 0, width, height);

        // Aplicar el tamaño de papel al formato de página
        pf.setPaper(paper);

        // Establecer el formato de página con el nuevo tamaño de papel en el trabajo de impresión
        job.setPrintable(this, pf);

        // Mostrar diálogo de impresión y verificar si se confirmó la impresión
        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                // Intentar imprimir el ticket
                job.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        if (page > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        Font font = new Font("Monospaced", Font.PLAIN, 8); // Tamaño de fuente ajustado
        g2d.setFont(font);

        int y = 20; // Posición inicial en el eje Y
        int ticketWidth = 158; // Ancho del ticket en puntos (56 mm)
        int rightMargin = 10; // Margen derecho
        int textX = 10; // Posición inicial del texto a la izquierda

        // Procesar cada línea del ticket
        for (String line : ticket.split("\n")) {
            line = line.replaceAll("[^\\x20-\\x7E]", ""); // Limpiar caracteres no imprimibles

            // Para líneas con total o subtotales
            if (line.contains("=") || line.startsWith("Total:")) {
                int stringWidth = g2d.getFontMetrics().stringWidth(line);
                int x = ticketWidth - stringWidth - rightMargin; // Calcular posición X para alineación a la derecha
                g2d.drawString(line, x, y);
            } else {
                String[] parts = line.split(" x "); // Suponiendo que el formato incluye " x "
                if (parts.length == 2) {
                    String product = parts[0].trim(); // Nombre del producto
                    String[] priceParts = parts[1].split(" - "); // Separar precio y descuento (si existe)
                    String quantity = priceParts[0].trim(); // Cantidad
                    String price = priceParts.length > 1 ? priceParts[1].trim() : ""; // Precio o descuento

                    // Ajustar el formato de la línea del producto
                    String productLine = String.format("%-20s  x %s", product, quantity); // Reducir el espacio entre nombre y cantidad
                    g2d.drawString(productLine, textX, y); // Dibuja el producto y cantidad

                    // Calcular espacio para alineación de precio
                    int priceX = ticketWidth - g2d.getFontMetrics().stringWidth(price) - rightMargin; // Posición del precio a la derecha
                    g2d.drawString(price, priceX, y); // Precio alineado a la derecha
                } else {
                    // Dibujar líneas sin cantidad
                    g2d.drawString(line, textX, y);
                }
            }
            y += 12; // Incremento ajustado para el espaciado entre líneas
        }

        return PAGE_EXISTS;
    }

    private String ajustarLineas(String line, int maxLength) {
        StringBuilder adjustedLine = new StringBuilder();
        String[] words = line.split(" ");
        int currentLineLength = 0;

        for (String word : words) {
            if (currentLineLength + word.length() > maxLength) {
                adjustedLine.append("\n");
                currentLineLength = 0;
            }
            adjustedLine.append(word).append(" ");
            currentLineLength += word.length() + 1; // +1 por el espacio
        }
        return adjustedLine.toString().trim();
    }
}