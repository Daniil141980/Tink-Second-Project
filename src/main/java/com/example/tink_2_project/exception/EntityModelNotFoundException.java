package com.example.tink_2_project.exception;

public class EntityModelNotFoundException extends RuntimeException {

    public EntityModelNotFoundException(String entity, String field, String valueField) {
        super(entity + " с " + field + ": " + valueField + " не найдено");
    }
}