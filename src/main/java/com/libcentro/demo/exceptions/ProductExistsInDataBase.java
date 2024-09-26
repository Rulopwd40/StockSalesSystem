package com.libcentro.demo.exceptions;

public class ProductExistsInDataBase extends RuntimeException {
    public ProductExistsInDataBase(String message) {
        super(message);
    }
}
