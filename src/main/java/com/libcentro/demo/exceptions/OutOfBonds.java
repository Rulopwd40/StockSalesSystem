package com.libcentro.demo.exceptions;

public class OutOfBonds extends RuntimeException {
    public OutOfBonds(String message) {
        super(message);
    }
}
