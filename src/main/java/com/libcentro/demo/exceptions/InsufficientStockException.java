package com.libcentro.demo.exceptions;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String mensaje) {
        super(mensaje);
    }
}
