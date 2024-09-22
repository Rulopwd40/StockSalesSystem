package com.libcentro.demo.utils.filters;

import javax.swing.*;
import javax.swing.text.AbstractDocument;

public abstract class Filter {
    //Filters
    public static void setIntegerFilter(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new IntegerFilter());
    }

    public static void setDoubleFilter(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DoubleFilter());
    }

    public static void setSymbolFilter(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new SymbolFilter());
    }
    public static void setPrecioFilter(JTextField textField){
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new PrecioFilter());
    }
}
