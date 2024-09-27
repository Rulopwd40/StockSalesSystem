package com.libcentro.demo.utils;

import com.libcentro.demo.exceptions.EmptyFieldException;
import com.libcentro.demo.exceptions.OutOfBounds;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class FieldAnalyzer {
    public enum TipoDato {
        FLOAT,
        INTEGER
    }

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
    public static boolean campoLleno(JTextField textField) throws EmptyFieldException {
        if (textField.getText().trim().isEmpty()) {
            throw new EmptyFieldException("Complete el campo "+ textField.getName());
        }
        return true;
    }

    public static TipoDato limites(JTextField textField, int min, int max) throws OutOfBounds {
        TipoDato tipo = null;
        Supplier<Boolean> isInteger = () -> {
            try {
                Integer.parseInt(textField.getText());

                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        };
        if (isInteger.get()) {
            int numero = Integer.parseInt(textField.getText());
            if(numero < min || numero > max) {
                throw new OutOfBounds("El numero debe estar entre [" + min + ";" + max + "]");
            }
            tipo = TipoDato.INTEGER;
        }
        else {
            float numero = Float.parseFloat(textField.getText());
            if(numero < min || numero > max) {
                throw new OutOfBounds("El numero debe estar entre [" + min + ";" + max + "]");
            }
            tipo = TipoDato.FLOAT;
        }
        return tipo;
    };

    }
