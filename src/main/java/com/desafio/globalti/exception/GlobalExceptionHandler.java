package com.desafio.globalti.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataDeTransferenciaInvalidaException.class)
    public ResponseEntity<String> handleDataDeTransferenciaInvalida() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data da transferência não deve ser antes da data de agendamento! Ajuste os campos e tente novamente.");
    }

    @ExceptionHandler(ContaDeDestinoInvalidaException.class)
    public ResponseEntity<String> handleContaDeDestinoInvalida() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A conta de destino não pode ser igual a de origem! Ajuste os campos e tente novamente.");
    }

    @ExceptionHandler(TaxaNaoAplicavelException.class)
    public ResponseEntity<String> handleTaxaNaoAplicavelException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Taxa não aplicável para essa data de transferência, tente um novo valor com no máximo 50 dias à partir da data de agendamento.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        Map<String, String> erros = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(
                error -> erros.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data inválida! Ajuste os cmapos e tente novamente.");
    }
}
