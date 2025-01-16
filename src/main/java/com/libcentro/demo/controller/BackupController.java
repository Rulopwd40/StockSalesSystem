package com.libcentro.demo.controller;

import com.libcentro.demo.services.interfaces.IbackupService;
import com.libcentro.demo.view.backup.BackupView;
import com.libcentro.demo.view.productos.ImportarCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

@Controller
public class BackupController {

    private final IbackupService backupService;

    private BackupView view;

    @Autowired
    public BackupController ( IbackupService backupService ){
        this.backupService = backupService;
    }

    public void backUpControl(){
        boolean bc= backupService.backupControl();
        if(bc){
            int value = JOptionPane.showConfirmDialog (null, "El ultimo backup se realizó hace mas de 2 dias, crear otro?");
            if (value == JOptionPane.YES_OPTION) {
                backupService.backup();
            }
        }
    }

    public void openBackupView(){
        if( view == null){
            view = new BackupView();
            addBackupViewListeners();

        }
        updateTable();


        view.setVisible(true);
    }

    private void updateTable (){

        String[] names = {"Nombre","Fecha"};
        DefaultTableModel tableModel = new DefaultTableModel(names, 0);
        List<String[]> backupList = backupService.getBackupList();


        backupList.forEach (bp->{
            tableModel.addRow(new String[]{bp[0],bp[1]});
                }
        );

        view.getBackupTable ().setModel(tableModel);
    }

    private void addBackupViewListeners (){
        view.getCrearBackupButton ().addActionListener (new ActionListener () {

            @Override
            public void actionPerformed ( ActionEvent e ){
                crearBackup();
            }
        });
        view.getBorrarButton ().addActionListener (new ActionListener () {
            @Override
            public void actionPerformed ( ActionEvent e ){
                borrar();
            }
        });
        view.getSalirButton ().addActionListener (new ActionListener () {

            @Override
            public void actionPerformed ( ActionEvent e ){
                view.setVisible(false);
            }
        });
    }

    private void borrar (){
        String nombre = obtenerSeleccionado();
        try{
            backupService.delete(nombre);
            updateTable();
        }catch( RuntimeException e ){
            JOptionPane.showMessageDialog(view, "Error al borrar el archivo");
        }
    }

    private void crearBackup (){
        try {
            backupService.backup ();
            JOptionPane.showMessageDialog (view,"Backup creado","Éxito",JOptionPane.INFORMATION_MESSAGE);
            updateTable ();
        }catch (RuntimeException e){
            JOptionPane.showMessageDialog (view,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private String obtenerSeleccionado (){
        int row = view.getBackupTable().getSelectedRow();
        if(row == -1) {
            JOptionPane.showMessageDialog (view,"Seleccione un backup a restaurar","Error",JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Seleccione un backup a restaurar");
        }
        return view.getBackupTable ().getValueAt(row, 0).toString();
    }
}
