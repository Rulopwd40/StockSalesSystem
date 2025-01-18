package com.libcentro.demo.controller;

import com.libcentro.demo.services.interfaces.IconfiguracionService;
import com.libcentro.demo.view.configuracion.ConfiguracionView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@Controller
public class ConfiguracionController {


    private final IconfiguracionService configuracionService;


    private ConfiguracionView view;
    private JTable table;

    @Autowired
    public ConfiguracionController ( IconfiguracionService configuracionService ){
        this.configuracionService = configuracionService;
    }

    public void openConfiguracionView(){
        if(view==null) {
            view = new ConfiguracionView ();
            view.initialize ();

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



}
