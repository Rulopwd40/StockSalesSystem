package com.libcentro.demo.utils;

import com.libcentro.demo.exceptions.EmptyFieldException;

import javax.swing.*;
import java.awt.*;

public class FieldAnalyzer {

    public static boolean todosLosCamposLlenos(Container container) throws EmptyFieldException {
        for (Component component : container.getComponents()) {
            if (component instanceof JTextField textField) {
                if (textField.getText().trim().isEmpty()) {
                    throw new EmptyFieldException("Complete todos los campos");
                }
            } else if (component instanceof Container) {
                // Si el componente es un contenedor, revisar sus componentes hijos de forma recursiva
                todosLosCamposLlenos((Container) component);
            }
        }
        return true;  // Todos los campos tienen contenido
    }


}
