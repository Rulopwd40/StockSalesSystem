package com.libcentro.demo.exceptions;

public class ProductNameExists extends RuntimeException {
    public ProductNameExists(String message) {
        super(message);
    }
}
