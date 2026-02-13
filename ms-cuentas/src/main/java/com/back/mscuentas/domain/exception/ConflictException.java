package com.back.mscuentas.domain.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) { super(message); }
}