package com.example.tink_2_project.exception;

public class BadTokenException extends RuntimeException {
    public BadTokenException() {
        super("Плохой токен");
    }
}
