package com.libcentro.demo.view.productos;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProgresoProductos extends JDialog {
    private JProgressBar progressBar;
    private JLabel labelStatus;

    public ProgresoProductos(Frame owner, int totalProductos) {
        super(owner, "Procesando Productos", true);
        setSize(400, 150);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());
        setUndecorated(true);

        labelStatus = new JLabel("Procesando 0 de " + totalProductos + " productos...");
        add(labelStatus, BorderLayout.NORTH);

        progressBar = new JProgressBar(0, totalProductos);
        progressBar.setStringPainted(true);
        add(progressBar, BorderLayout.CENTER);

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setVisible(false); // Se mostrará cuando comience el proceso.
    }

    public void actualizarProgreso(int productosProcesados, int totalProductos) {
        labelStatus.setText("Procesando " + productosProcesados + " de " + totalProductos + " productos...");
        progressBar.setValue(productosProcesados);
    }

    public void finalizar() {
        labelStatus.setText("Proceso finalizado");
        progressBar.setValue(progressBar.getMaximum());
    }


    public void mostrar() {
        setVisible(true);
    }


    public void cerrar() {
        setVisible(false);
        dispose();
    }

}