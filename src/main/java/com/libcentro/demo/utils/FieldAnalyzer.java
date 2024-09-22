package com.libcentro.demo.utils;

import javax.swing.*;
import java.awt.*;

public class FieldAnalyzer {

    public static boolean todosLosCamposLlenos(JDialog dialog) {
        for(Component component: dialog.getComponents()) {
            if (component instanceof JTextField textField) {
                if (textField.getText().trim().isEmpty()) {
                    return false;  // Al menos un campo está vacío
                }
            }
        }
        return true;  // Todos los campos tienen contenido
    }


}
