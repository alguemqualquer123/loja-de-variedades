package com.example.lojadevariedades.exception;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

import com.example.lojadevariedades.utils.ResponseJson;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseJson> handleCustom(CustomException ex, HttpServletRequest req) {
        ResponseJson body = ResponseJson.error(ex.getMessage(), ex.getCode());
        body.setPath(req.getRequestURI());
        body.setTimestamp(java.time.OffsetDateTime.now().toString());
        body.setData(ex.getDetails());
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseJson> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining("; "));
        ResponseJson body = ResponseJson.error(msg, "VALIDATION_ERROR");
        body.setPath(req.getRequestURI());
        body.setTimestamp(java.time.OffsetDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseJson> handleConstraint(ConstraintViolationException ex, HttpServletRequest req) {
        String msg = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining("; "));
        ResponseJson body = ResponseJson.error(msg, "CONSTRAINT_VIOLATION");
        body.setPath(req.getRequestURI());
        body.setTimestamp(java.time.OffsetDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseJson> handleUnreadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
        ResponseJson body = ResponseJson.error("Corpo da requisição inválido", "BAD_REQUEST");
        body.setPath(req.getRequestURI());
        body.setTimestamp(java.time.OffsetDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseJson> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest req) {
        ResponseJson body = ResponseJson.error("Violação de integridade de dados", "DATA_INTEGRITY");
        body.setPath(req.getRequestURI());
        body.setTimestamp(java.time.OffsetDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseJson> handleNotFound(NoSuchElementException ex, HttpServletRequest req) {
        ResponseJson body = ResponseJson.error("Recurso não encontrado", "NOT_FOUND");
        body.setPath(req.getRequestURI());
        body.setTimestamp(java.time.OffsetDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseJson> handleAccessDenied(AccessDeniedException ex, HttpServletRequest req) {
        ResponseJson body = ResponseJson.error("Acesso negado", "FORBIDDEN");
        body.setPath(req.getRequestURI());
        body.setTimestamp(java.time.OffsetDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseJson> handleGeneric(Exception ex, HttpServletRequest req) {
        ResponseJson body = ResponseJson.error("Erro interno do servidor", "INTERNAL_ERROR");
        body.setPath(req.getRequestURI());
        body.setTimestamp(java.time.OffsetDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
