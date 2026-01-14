package com.pranathi.taskmanager.exception;

import com.pranathi.taskmanager.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1️⃣ Validation errors (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        String errorMessage =
                ex.getBindingResult()
                        .getFieldError()
                        .getDefaultMessage();

        ErrorResponse error =
                new ErrorResponse(errorMessage, HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(error);
    }

    // 2️⃣ Business logic errors
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex) {

        ErrorResponse error =
                new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(error);
    }

    // 3️⃣ Fallback (unexpected errors)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {

        ErrorResponse error =
                new ErrorResponse(
                        "Something went wrong",
                        HttpStatus.INTERNAL_SERVER_ERROR.value()
                );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}
