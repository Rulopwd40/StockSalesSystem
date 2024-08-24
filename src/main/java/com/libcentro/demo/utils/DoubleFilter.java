package com.libcentro.demo.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class DoubleFilter extends DocumentFilter {
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String newText = new StringBuilder(currentText).replace(offset, offset + length, text).toString();

        if (newText.matches("\\d*(,\\d*)?")) { // Permite solo dígitos y una sola coma
            super.replace(fb, offset, length, text, attrs);
        }

    }

    @Override
    public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String newText = new StringBuilder(currentText).insert(offset, text).toString();

        if (newText.matches("\\d*(,\\d*)?")) { // Permite solo dígitos y una sola coma
            super.insertString(fb, offset, text, attrs);
        }

    }
}
