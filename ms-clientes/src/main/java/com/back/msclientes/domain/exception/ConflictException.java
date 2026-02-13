package com.back.msclientes.domain.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) { super(message); }
}