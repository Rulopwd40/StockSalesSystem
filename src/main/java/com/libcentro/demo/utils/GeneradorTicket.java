package com.libcentro.demo.utils;

import com.libcentro.demo.model.ProductoFStock;
import com.libcentro.demo.model.Venta;
import com.libcentro.demo.model.Venta_Producto;

import java.awt.*;
import java.awt.print.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneradorTicket implements Printable {

    private static final int ANCHO_LINEA = 40;
    private static final String SEPARADOR = "-".repeat(ANCHO_LINEA);
    private static final int ANCHO_TICKET = 55;

    private static final String TEXTO_TICKET = "Libreria Centro\n" +
            "Fecha: %s\n" +
            "Venta N°: %d\n" +
            "-----------------------------\n" +
            "%s\n" +
            "-----------------------------\n" +
            "Total: %s\n";

    String ticket;

    public GeneradorTicket() {
    }

    public String generarTicket(Venta venta) {
        StringBuilder productos = new StringBuilder();


        for (Venta_Producto ventaProducto : venta.getVenta_productos()) {
            String nombre = ventaProducto.getProducto().getNombre().toUpperCase();
            String precio = String.format("$%.2f", ventaProducto.getProducto().getPrecio_venta());
            String cantidad = String.valueOf(ventaProducto.getCantidad());
            String descuento = (ventaProducto.getDescuento() > 0)
                    ? String.format("- $%.2f", (ventaProducto.getProducto().getPrecio_venta() * ventaProducto.getCantidad() * ventaProducto.getDescuento() / 100))
                    : "";
            String porcentaje = (ventaProducto.getDescuento() > 0) ? String.format("%.2f",ventaProducto.getDescuento()) : "";
            String total = String.format("= $%.2f", ventaProducto.getTotal());


            productos.append(String.format("%-30s\n", nombre));

            productos.append(String.format("%-5s x %-2s\n", precio, cantidad));


            if (!descuento.isEmpty()) {
                productos.append(String.format("    %s(%s%%)\n", descuento,porcentaje));
            }

            // Alinear el total a la derecha
            productos.append(String.format("%" + ANCHO_TICKET + "s\n", total));
        }
        for (ProductoFStock productoF : venta.getProductoFStocks()) {
            String nombreF = productoF.getNombre().toUpperCase();
            String precioF = String.format("$%.2f", productoF.getPrecio_venta());
            String cantidadF = String.valueOf(productoF.getCantidad());
            String descuentoF = (productoF.getDescuento() > 0)
                    ? String.format("- $%.2f", (productoF.getPrecio_venta() * productoF.getCantidad() * productoF.getDescuento() / 100))
                    : "";
            String porcentajeF = (productoF.getDescuento() > 0) ? String.format("%.2f", productoF.getDescuento()) : "";
            String totalF = String.format("= $%.2f", productoF.getPrecio_venta() * productoF.getCantidad() - (productoF.getPrecio_venta() * productoF.getCantidad() * productoF.getDescuento() / 100));


            productos.append(String.format("%-30s\n", nombreF));

            productos.append(String.format("%-5s x %-2s\n", precioF, cantidadF));

            if (!descuentoF.isEmpty()) {
                productos.append(String.format("    %s(%s%%)\n", descuentoF, porcentajeF));
            }

            // Alinear el total a la derecha
            productos.append(String.format("%" + ANCHO_TICKET + "s\n", totalF));
        }

        // Calcular el total final y formatearlo
        double totalVenta = venta.getTotal();
        String totalFormateado = String.format("$%.2f", totalVenta); // Formatear el total


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fechaFormateada = sdf.format(new Date());


        ticket = String.format(TEXTO_TICKET, fechaFormateada, venta.getId(), productos.toString(), totalFormateado);

        System.out.println(ticket);
        return ticket;
    }

    public void imprimirTicket() {
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = job.defaultPage();
        Paper paper = new Paper();

        double width = 56 * 2.83;
        double height = 10000;
        paper.setSize(width, height);

        paper.setImageableArea(0, 0, width, height);

        pf.setPaper(paper);


        job.setPrintable(this, pf);

        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
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

        Font font = new Font("Monospaced", Font.PLAIN, 8);
        g2d.setFont(font);

        int y = 20;
        int ticketWidth = 158;
        int rightMargin = 10;
        int textX = 10;


        for (String line : ticket.split("\n")) {
            line = line.replaceAll("[^\\x20-\\x7E]", "");
            if (line.contains("=") || line.startsWith("Total:")) {
                int stringWidth = g2d.getFontMetrics().stringWidth(line);
                int x = ticketWidth - stringWidth - rightMargin;
                g2d.drawString(line, x, y);
            } else {
                String[] parts = line.split(" x ");
                if (parts.length == 2) {
                    String product = parts[0].trim();
                    String[] priceParts = parts[1].split(" - ");
                    String quantity = priceParts[0].trim();
                    String price = priceParts.length > 1 ? priceParts[1].trim() : "";


                    String productLine = String.format("%-20s  x %s", product, quantity);
                    g2d.drawString(productLine, textX, y);

                    int priceX = ticketWidth - g2d.getFontMetrics().stringWidth(price) - rightMargin;
                    g2d.drawString(price, priceX, y);
                } else {

                    g2d.drawString(line, textX, y);
                }
            }
            y += 12;
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