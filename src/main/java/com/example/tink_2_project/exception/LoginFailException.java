package com.example.tink_2_project.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginFailException extends AuthenticationException {
    public LoginFailException() {
        super("Не удалось верифицировать ваш аккаунт");
    }
}
