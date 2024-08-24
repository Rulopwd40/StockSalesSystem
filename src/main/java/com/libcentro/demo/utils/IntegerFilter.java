package com.libcentro.demo.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class IntegerFilter extends DocumentFilter {

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text.matches("\\d*")) { // Verifica si el texto es un número
            super.replace(fb, offset, length, text, attrs);
        }
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
        if (text.matches("\\d*")) { // Verifica si el texto es un número
            super.insertString(fb, offset, text, attrs);
        }
    }
}
