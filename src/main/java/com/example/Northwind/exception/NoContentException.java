package com.example.Northwind.exception;

public class NoContentException extends RuntimeException {
    public NoContentException(String message) {
        super(message);
    }
}