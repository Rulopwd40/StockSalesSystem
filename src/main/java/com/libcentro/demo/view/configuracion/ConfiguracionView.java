package com.libcentro.demo.view.configuracion;

import javax.swing.*;

public class ConfiguracionView extends JFrame {
    private JButton cancelarButton;
    private JButton guardarButton;
    private JTable configTable;
    private JPanel panel;

    public ConfiguracionView() {
        super("Configuracion");


    }

    public void initialize(){
        setContentPane (panel);
        setLocationRelativeTo(null);
        pack ();

    }

    public JButton getCancelarButton (){
        return cancelarButton;
    }

    public JButton getGuardarButton (){
        return guardarButton;
    }

    public JTable getConfigTable (){
        return configTable;
    }
}
