package com.task.routeadvisor.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> unexpectedException(final RuntimeException exception) {
        log.error("Unexpected exception occurred: ", exception);
        final Map<String, Object> responseBody = Map.of(
            "status", HttpStatus.INTERNAL_SERVER_ERROR.name(),
            "code", HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "message", exception.getMessage()
        );
        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> statusException(final ResponseStatusException exception) {
        final Map<String, Object> responseBody = Map.of(
            "status", exception.getStatus().name(),
            "code", exception.getStatus().value(),
            "message", exception.getReason()
        );
        return new ResponseEntity<>(responseBody, exception.getStatus());
    }

}
