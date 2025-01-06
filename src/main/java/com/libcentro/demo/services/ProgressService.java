package com.libcentro.demo.services;

import com.libcentro.demo.services.interfaces.IprogressService;

import javax.swing.*;
import java.util.List;
import java.util.function.Consumer;

public class ProgressService<T> implements IprogressService<T> {
    private final JDialog progresoDialog;
    private final JProgressBar progressBar;
    private SwingWorker<Void, Integer> worker;

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
        worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                SwingUtilities.invokeLater(() -> progresoDialog.setVisible(true));

                for (int i = 0; i < items.size(); i++) {
                    try {
                        task.accept(items.get(i));

                        publish(i + 1);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al procesar el elemento: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                    get(); // Asegura que no hubo errores
                    JOptionPane.showMessageDialog(null, "Proceso completado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error durante el procesamiento: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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