package com.libcentro.demo.utils;

import com.libcentro.demo.model.ProductoFStock;
import com.libcentro.demo.model.Venta;
import com.libcentro.demo.model.Venta_Producto;
import com.libcentro.demo.model.json.TicketData;

import java.awt.*;
import java.awt.print.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneradorTicket implements Printable {

    TicketData ticketData;
    String ticket;

    public GeneradorTicket() {
    }



    public String generarTicket( Venta venta, TicketData ticketData ) {
        this.ticketData = ticketData;
        int caracteres = (int) (ticketData.getAncho ()/ 2.4);
        String separador = "-".repeat(caracteres);;
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
            int anchoCaracteres = (int) (ticketData.getAncho() / 2.4);
            productos.append(String.format("%" + anchoCaracteres + "s\n", total));
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
            //productos.append(String.format("%" + ticketData.getAncho () + "s\n", totalF));
            int anchoCaracteres = (int) (ticketData.getAncho() / 2.4);
            productos.append(String.format("%" + anchoCaracteres + "s\n", totalF));
        }

        // Calcular el total final y formatearlo
        double totalVenta = venta.getTotal();
        String totalFormateado = String.format("$%.2f", totalVenta); // Formatear el total


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String fechaFormateada = sdf.format(new Date());

        ticket = ticketData.getContenido()
                .replace("{{fecha}}", fechaFormateada)
                .replace("{{idVenta}}", venta.getId() == null ? "NULL" : venta.getId())
                .replace("{{productos}}", productos.toString())
                .replace("{{total}}", totalFormateado)
                .replace("{{linea}}", separador);

        ticket = aplicarAlineacion(ticket, ticketData.getAncho());
        //System.out.println(ticket);
        return ticket;
    }

    public void imprimirTicket() {
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = job.defaultPage();
        Paper paper = new Paper();

        double width = ticketData.getAncho() * 2.83;
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

        // Convertir mm a puntos para el ancho de impresión
        double anchoMm = ticketData.getAncho();
        int ticketWidth = (int) ((anchoMm / 25.4) * 72);

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

    public String aplicarAlineacion(String contenido, double anchoMm) {
        int ancho = mmToChars(anchoMm);
        StringBuilder resultado = new StringBuilder();

        for (String linea : contenido.split("\n")) {
            if (linea.startsWith("{{C}}")) {
                resultado.append(centerAligned(linea.substring(5), ancho)).append("\n");
            } else if (linea.startsWith("{{R}}")) {
                resultado.append(rightAligned(linea.substring(5), ancho)).append("\n");
            } else {
                resultado.append(linea).append("\n"); // Línea sin marcador
            }
        }

        return resultado.toString();
    }

    public String rightAligned(String text, double anchoMm) {
        if (text == null) text = "";
        int chars = mmToChars(anchoMm);
        if (text.length() > chars) {
            text = text.substring(0, chars);
        }
        return String.format("%" + chars + "s", text);
    }

    public String centerAligned(String text, double anchoMm) {
        int width = mmToChars(anchoMm);
        int padding = (width - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }

    public int mmToChars(double mm) {
        double mmPorCaracter = 1.4; // puede variar según impresora y fuente
        return (int) (mm / mmPorCaracter);
    }
}