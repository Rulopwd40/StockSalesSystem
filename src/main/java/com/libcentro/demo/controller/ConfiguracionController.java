package com.libcentro.demo.controller;

import com.libcentro.demo.model.Producto;
import com.libcentro.demo.model.ProductoFStock;
import com.libcentro.demo.model.Venta;
import com.libcentro.demo.model.Venta_Producto;
import com.libcentro.demo.services.interfaces.IconfiguracionService;
import com.libcentro.demo.utils.GeneradorTicket;
import com.libcentro.demo.view.configuracion.ConfiguracionView;
import com.libcentro.demo.view.configuracion.TicketDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;

@Controller
public class ConfiguracionController {


    private final IconfiguracionService configuracionService;
    private GeneradorTicket generadorTicket;

    private ConfiguracionView view;
    private TicketDialog ticketDialog;
    private Venta ventaExample;

    private JTable table;

    @Autowired
    public ConfiguracionController ( IconfiguracionService configuracionService ){
        this.configuracionService = configuracionService;
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
        if(ticketDialog == null){
            ticketDialog = new TicketDialog();
            ticketDialog.setLocationRelativeTo(null);
            ticketDialogAddListeners();
            ticketDialog.setSize (1300, 800);
        }



        ticketDialog.setVisible(true);
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
        ticketDialog.getTicketStructureArea ().getDocument ().addDocumentListener (new DocumentListener () {

            @Override
            public void insertUpdate ( DocumentEvent e ){
                updatePreview();
            }

            @Override
            public void removeUpdate ( DocumentEvent e ){
                updatePreview();
            }

            @Override
            public void changedUpdate ( DocumentEvent e ){
                updatePreview();
            }
        });
    }


    private void textAddComponent ( String component ){
        String finalComponent = "{{" + component + "}}";
        int posicionCursor = ticketDialog.getTicketStructureArea ().getCaretPosition();
        ticketDialog.getTicketStructureArea ().insert(finalComponent, posicionCursor);
    }

    private void updatePreview (){
        generadorTicket = new GeneradorTicket ();
        if(ventaExample == null){
            ventaExample = new Venta ();

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

        generadorTicket.setTextoTicket (ticketDialog.getTicketStructureArea ().getText ());
        String ticket = generadorTicket.generarTicket (ventaExample);

        ticketDialog.getPreviewArea ().setText(ticket);


    }

}
