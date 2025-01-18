package com.libcentro.demo.view.backup;

import javax.swing.*;

public class BackupView extends JFrame {
    private JPanel panel;
    private JButton crearBackupButton;
    private JButton borrarButton;
    private JButton salirButton;
    private JTable backupTable;


    public BackupView() {
        super("Backup");
    }

    public void initialize(){
        setContentPane (panel);
        pack ();
        setLocationRelativeTo (null);
    }

     public JButton getCrearBackupButton (){
        return crearBackupButton;
    }

    public JButton getBorrarButton (){
        return borrarButton;
    }

    public JButton getSalirButton (){
        return salirButton;
    }

    public JTable getBackupTable (){
        return backupTable;
    }

}
