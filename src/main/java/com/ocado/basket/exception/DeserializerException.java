package com.ocado.basket.exception;

public class DeserializerException extends Exception{
    public DeserializerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeserializerException(String message) {
        super(message);
    }
}
