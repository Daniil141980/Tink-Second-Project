package com.example.tink_2_project.exception;

public class AccountAlreadyExistException extends RuntimeException {
    public AccountAlreadyExistException(String nickname) {
        super("Аккаунт с именем: " + nickname + " уже существует");
    }
}
