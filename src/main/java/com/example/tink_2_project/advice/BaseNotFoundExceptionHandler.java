package com.example.tink_2_project.advice;

import com.example.tink_2_project.exception.EntityModelNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class BaseNotFoundExceptionHandler {

    @ExceptionHandler(EntityModelNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundException(EntityModelNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getMessage()));
    }

    public record ErrorResponse(String message) {}
}
