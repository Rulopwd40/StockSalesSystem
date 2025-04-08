package com.libcentro.demo.services;

import com.libcentro.demo.services.interfaces.ialertService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

@Service
public class SwingService implements ialertService {

    @Override
    public void executeWithDialog (
            Supplier<Boolean> command,
            Runnable onSuccess,
            Runnable onFailure
    ) {
        // Crear el dialogo modal
        JDialog dialogoEspera = new JDialog((Frame) null, "Procesando...", true);
        dialogoEspera.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialogoEspera.setSize(200, 100);
        dialogoEspera.setLocationRelativeTo(null);
        dialogoEspera.setLayout(new BorderLayout ());
        dialogoEspera.add(new JLabel("Espere, por favor...", SwingConstants.CENTER), BorderLayout.CENTER);

        // Ejecutar en segundo plano
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() {
                return command.get();
            }

            @Override
            protected void done() {
                dialogoEspera.dispose();
                try {
                    if (get()) {
                        onSuccess.run();
                    } else {
                        onFailure.run();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Ocurrió un error al ejecutar la operación.");
                }
            }
        };

        worker.execute();
        dialogoEspera.setVisible(true);
    }
}
