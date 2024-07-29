package com.example.Northwind.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String entityName, String fieldName, Object value) {
        super(entityName + " already exists with " + fieldName + ": " + value);
    }
}