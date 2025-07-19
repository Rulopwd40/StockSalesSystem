package com.libcentro.demo.controller;

import com.libcentro.demo.config.AppConfig;
import com.libcentro.demo.model.Producto;
import com.libcentro.demo.model.ProductoFStock;
import com.libcentro.demo.model.Venta;
import com.libcentro.demo.model.Venta_Producto;
import com.libcentro.demo.model.json.TicketData;
import com.libcentro.demo.services.JsonService;
import com.libcentro.demo.services.interfaces.IconfiguracionService;
import com.libcentro.demo.utils.GeneradorTicket;
import com.libcentro.demo.utils.filters.Filter;
import com.libcentro.demo.utils.filters.IntegerFilter;
import com.libcentro.demo.view.configuracion.ConfiguracionView;
import com.libcentro.demo.view.configuracion.TicketDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Controller
public class ConfiguracionController {


    private final IconfiguracionService configuracionService;
    private final JsonService<TicketData> jsonService;
    private GeneradorTicket generadorTicket;

    private ConfiguracionView view;
    private TicketDialog ticketDialog;
    private Venta ventaExample;

    private JTable table;
    private TicketData ticketData;


    @Autowired
    public ConfiguracionController ( IconfiguracionService configuracionService, JsonService<TicketData> jsonService ){
        this.configuracionService = configuracionService;
        this.jsonService = jsonService;
    }

    public void openConfiguracionView(){
        if(view==null) {
            view = new ConfiguracionView ();

            createTable ();
            configViewAddListeners ();
            updateTable();
        }

        view.setVisible(true);
    }



    private void createTable (){
        table = view.getConfigTable ();
        table.setModel(new javax.swing.table.DefaultTableModel(
                null,
                new String[]{"", ""}
        ) {
            @Override
            public boolean isCellEditable ( int row, int column ){
                return column != 0;
            }
        });
    }

    private void configViewAddListeners (){
        view.getGuardarButton ().addActionListener (new ActionListener () {
            @Override
            public void actionPerformed ( ActionEvent e ){
                guardarConfiguracion();
            }
        });
        view.getCancelarButton ().addActionListener (new ActionListener () {
            @Override
            public void actionPerformed ( ActionEvent e ){
                view.setVisible (false);
            }
        });
        view.getTicketsButton ().addActionListener (new ActionListener () {
            @Override
            public void actionPerformed ( ActionEvent e ){
                openTicketDialog();
            }
        });
    }

    private void updateTable (){
        Map<String,String> config = configuracionService.obtenerConfiguracion ();
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel ();

        tableModel.setRowCount(0);

        for (Map.Entry<String, String> map : config.entrySet ()){
            tableModel.addRow(new String[]{map.getKey(), map.getValue()});
        }
    }

    private void guardarConfiguracion (){
        Map<String,String> config = new HashMap<> ();

        DefaultTableModel tableModel = (DefaultTableModel) table.getModel ();

        for(Vector data : tableModel.getDataVector ()){
            config.putIfAbsent (data.get (0).toString (),data.get(1).toString ());
        }
        try{
            configuracionService.actualizarConfiguracion (config);
            JOptionPane.showMessageDialog (view, "Configuracion actualizada");
        }catch(Exception e){
            JOptionPane.showMessageDialog (view, "Error al guardar la configuracion");
        }
    }

    //Ticket Dialog
    private void openTicketDialog (){
        if(ticketDialog == null) {
            ticketDialog = new TicketDialog ();
            ticketDialog.setLocationRelativeTo (null);
            ticketDialogAddListeners ();
            ticketDialog.setSize (1300, 800);
            Filter.setIntegerFilter (ticketDialog.getAnchoField ());
        }

        loadTicket();

        ticketDialog.setVisible(true);
    }

    private void loadTicket (){
        try {
            System.out.println ("LoadTicket");
            ticketData = jsonService.loadFromFile (AppConfig.ticket_path, TicketData.class);
        }catch (Exception e){
            JOptionPane.showMessageDialog (null,
                    "Error al cargar la configuración de ticket: " + e.getMessage (),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ticketData = new TicketData ();
            ticketData.setAncho (58);
            return;
        }
        System.out.println (ticketData);
        ticketDialog.getAnchoField ().setText (String.valueOf (ticketData.getAncho()));
        ticketDialog.getTicketStructureArea ().setText(ticketData.getContenido ());
        updateTicket ();
        System.out.println ("Ticket Cargado");

    }
    private void ticketDialogAddListeners (){
        ticketDialog.getButtonOK ().addActionListener (new ActionListener () {
            @Override
            public void actionPerformed ( ActionEvent e ){
                ticketDialog.setVisible (false);
            }
        });

        ticketDialog.getFechaButton ().addActionListener (new ActionListener () {
            @Override
            public void actionPerformed ( ActionEvent e ){
                textAddComponent("fecha");
            }
        });
        ticketDialog.getIdVentaButton ().addActionListener (new ActionListener () {
            @Override
            public void actionPerformed ( ActionEvent e ){
                textAddComponent("idVenta");
            }
        });
        ticketDialog.getProductosButton ().addActionListener (new ActionListener () {
            @Override
            public void actionPerformed ( ActionEvent e ){
                textAddComponent("productos");
            }
        });
        ticketDialog.getTotalButton ().addActionListener (new ActionListener () {
            @Override
            public void actionPerformed ( ActionEvent e ){
                textAddComponent("total");
            }
        });
        ticketDialog.getLineaButton ().addActionListener (new ActionListener () {
            @Override
            public void actionPerformed ( ActionEvent e ){
                textAddComponent("linea");
            }
        });

        ticketDialog.getGuardarButton ().addActionListener (new ActionListener () {
            @Override
            public void actionPerformed ( ActionEvent e ){
                saveTicket();
            }
        });

        ticketDialog.getReestablecerButton ().addActionListener (new ActionListener () {
            @Override
            public void actionPerformed ( ActionEvent e ){
                loadTicket();
            }
        });

        ticketDialog.getPruebaButton ().addActionListener (new ActionListener () {
            @Override
            public void actionPerformed ( ActionEvent e ){
                imprimirPrueba();
            }
        });
        //DOCS
        ticketDialog.getTicketStructureArea ().getDocument ().addDocumentListener (new DocumentListener () {
            @Override
            public void insertUpdate ( DocumentEvent e ){
                ticketData.setContenido (ticketDialog.getTicketStructureArea ().getText());
                updateTicket();
            }

            @Override
            public void removeUpdate ( DocumentEvent e ){
                ticketData.setContenido (ticketDialog.getTicketStructureArea ().getText());
                updateTicket();
            }

            @Override
            public void changedUpdate ( DocumentEvent e ){
                ticketData.setContenido (ticketDialog.getTicketStructureArea ().getText());
                updateTicket ();
            }
        });
        ticketDialog.getAnchoField ().getDocument ().addDocumentListener (new DocumentListener () {

            @Override
            public void insertUpdate ( DocumentEvent e ){
               cambiarAnchoTicket ();
            }

            @Override
            public void removeUpdate ( DocumentEvent e ){
                cambiarAnchoTicket ();
            }

            @Override
            public void changedUpdate ( DocumentEvent e ){
                cambiarAnchoTicket ();
            }
        });
    }

    private void imprimirPrueba (){
        if(ventaExample == null){
            generarVenta ();
        }
        generadorTicket.generarTicket (ventaExample,ticketData);
        generadorTicket.imprimirTicket ();
    }

    private void saveTicket (){
            try{
                jsonService.saveToFile (AppConfig.ticket_path,ticketData);
            } catch (NumberFormatException e){
                JOptionPane.showMessageDialog (view, "El valor no es válido");
            } catch (IOException e) {
                JOptionPane.showMessageDialog (view, "Error al guardar el ticket");
            }
    }

    private void cambiarAnchoTicket(){
        String ancho = ticketDialog.getAnchoField ().getText ();
        if(ancho.isEmpty ()) return;
        ticketData.setAncho (Integer.parseInt (ticketDialog.getAnchoField ().getText ()));
        updateTicket ();

    }

    private void textAddComponent ( String component ){
        String finalComponent = "{{" + component + "}}";
        int posicionCursor = ticketDialog.getTicketStructureArea ().getCaretPosition();
        ticketDialog.getTicketStructureArea ().insert(finalComponent, posicionCursor);
    }

    private void updateTicket (){
        int dpi = Toolkit.getDefaultToolkit().getScreenResolution();


        ticketDialog.getPreviewPanel ().setPreferredSize(new Dimension((int) (Math.round(((ticketData.getAncho () / 25.4) * 100) / 100) * dpi),200));
        ticketDialog.getPreviewPanel ().revalidate ();
        ticketDialog.getPreviewPanel ().repaint ();

        ticketDialog.getTicketPanel ().setPreferredSize(new Dimension((int) (Math.round(((ticketData.getAncho () / 25.4) * 100) / 100) * dpi),200));
        ticketDialog.getTicketPanel ().revalidate ();
        ticketDialog.getTicketPanel ().repaint ();


        if(generadorTicket == null) generadorTicket = new GeneradorTicket ();
        if(ventaExample == null){
            generarVenta ();
        }

        String ticket = generadorTicket.generarTicket (ventaExample,ticketData);

        ticketDialog.getPreviewArea ().setText(ticket);
    }

    private void generarVenta (){
        ventaExample = new Venta ();

        ventaExample.setId (generarVentaID ());
        Producto producto = new Producto("1234567890123", "Cuaderno A4", null, 500.0, 800.0, 1);
        ProductoFStock productoFStock = new ProductoFStock(10, 2, 800.0, "Cuaderno A4", 1);

        Venta_Producto ventaProducto = new Venta_Producto ();
        ventaProducto.setProducto (producto,5);
        ventaProducto.setDescuento (5);

        HashSet<Venta_Producto> ventaSet = new HashSet<>();
        ventaSet.add(ventaProducto);
        ventaExample.setVenta_productos(ventaSet);

        HashSet<ProductoFStock> productoFStockSet = new HashSet<>();
        productoFStockSet.add(productoFStock);
        ventaExample.setProductoFStocks(productoFStockSet);

        double total = 0;
        for(Venta_Producto vp : ventaExample.getVenta_productos()){
            total = total + vp.getTotal ();
        }
        for(ProductoFStock pfs : ventaExample.getProductoFStocks()){
            total = total + Math.round((pfs.getPrecio_venta () - (pfs.getPrecio_venta () * pfs.getDescuento ())/100d)*100d)/100d;
        }
    }

    private String generarVentaID (){
        String fechaActual = new SimpleDateFormat ("yyyyMMdd").format(new Date ());

        LocalDateTime inicioDelDia = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime finDelDia = LocalDateTime.now().with(LocalTime.MAX);

        String cuenta = 0+ "";

        return fechaActual + "-" + cuenta;
    }

}
