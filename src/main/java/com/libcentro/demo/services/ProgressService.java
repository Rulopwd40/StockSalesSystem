package com.libcentro.demo.services;

import com.libcentro.demo.services.interfaces.IprogressService;

import javax.swing.*;
import java.util.List;
import java.util.function.Consumer;

public class ProgressService<T> implements IprogressService<T> {
    private final JDialog progresoDialog;
    private final JProgressBar progressBar;
    private SwingWorker<Void, Integer> worker;
    private int errorCount=0;

    public ProgressService(JFrame parentFrame, int total) {
        // Configurar el diálogo de progreso
        progresoDialog = new JDialog(parentFrame, "Progreso", true);
        progresoDialog.setSize(400, 100);
        progresoDialog.setLocationRelativeTo(parentFrame);
        progresoDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        // Configurar la barra de progreso
        progressBar = new JProgressBar(0, total);
        progressBar.setStringPainted(true);

        progresoDialog.add(progressBar);
    }

    @Override
    public void ejecutarProceso ( List<T> items, Consumer<T> task ) {
        errorCount=0;
        worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                SwingUtilities.invokeLater(() -> progresoDialog.setVisible(true));

                for (int i = 0; i < items.size(); i++) {
                    try {
                        task.accept(items.get(i));

                        publish(i + 1);
                    } catch (RuntimeException e) {
                        errorCount++;
                    }
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int currentProgress = chunks.get(chunks.size() - 1);
                actualizarProgreso(currentProgress);
            }

            @Override
            protected void done() {
                finalizar();
                try {
                    if(errorCount >0){
                        throw new RuntimeException("Hay productos que no fueron actualizados. Total: " + errorCount);
                    }
                    get(); // Asegura que no hubo errores
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error durante el procesamiento: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    throw new RuntimeException ("Error");
                }
            }
        };
        worker.execute();
    }


    private void actualizarProgreso(int current) {
        SwingUtilities.invokeLater(() -> {
            progressBar.setValue(current);
            progressBar.setString(String.format("Progreso: %d/%d", current, progressBar.getMaximum()));
        });
    }

    private void finalizar() {
        SwingUtilities.invokeLater(progresoDialog::dispose);
    }
}