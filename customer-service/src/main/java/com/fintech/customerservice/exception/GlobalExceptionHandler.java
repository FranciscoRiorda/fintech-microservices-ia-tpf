package com.fintech.customerservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ClienteNoEncontradoException.class)
    public ResponseEntity<String> handleClienteNoEncontrado(ClienteNoEncontradoException ex) {
        log.warn("Cliente no encontrado: {}", ex.getMessage()); // Log with WARN level
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        log.error("Error no controlado: ", ex); // Log the full exception with ERROR level
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Error interno del servidor");
    }
}
