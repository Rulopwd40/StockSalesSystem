package com.libcentro.demo.utils.filters;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class SymbolFilter extends DocumentFilter {

    @Override
    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
        if (text.matches("[a-zA-Z\\s]*")) { // Solo permite letras y espacios
            super.insertString(fb, offset, text, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text.matches("[a-zA-Z\\s\\d]*")) { // Solo permite letras y espacios
            super.replace(fb, offset, length, text, attrs);
        }
    }
}
