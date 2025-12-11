package com.antoniojr.customer_crud.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.antoniojr.customer_crud.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
    StandardError error = new StandardError();
    error.setTimestamp(Instant.now());
    error.setStatus(HttpStatus.NOT_FOUND.value());
    error.setError("Resource not found.");
    error.setMsg(e.getMessage());
    error.setPath(request.getRequestURI());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<StandardError> handleResponseStatus(ResponseStatusException e, HttpServletRequest request) {
    StandardError error = new StandardError();
    error.setTimestamp(Instant.now());
    error.setStatus(HttpStatus.CONFLICT.value());
    error.setError("Conflict");
    error.setMsg(e.getReason());
    error.setPath(request.getRequestURI());
    return ResponseEntity.status(e.getStatusCode()).body(error);
  }
}
