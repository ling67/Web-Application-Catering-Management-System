package com.example.catering.controllers.advice;

import com.example.catering.dto.MessageDetails;
import com.example.catering.exceptions.ResourceNotFoundException;
import com.example.catering.exceptions.InsertErrorException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ExceptionControlAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MessageDetails> handleBookNotFoundException(ResourceNotFoundException ex) {
        MessageDetails msg = new MessageDetails(ex.getMessage(), false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
    }

    @ExceptionHandler(InsertErrorException.class)
    public ResponseEntity<MessageDetails> InsertErrorException(InsertErrorException ex) {
        MessageDetails msg = new MessageDetails(ex.getMessage(), false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MessageDetails> handleRuntimeException(RuntimeException ex) {
        MessageDetails msg = new MessageDetails(ex.getMessage(), false);
        return ResponseEntity.internalServerError().body(msg);
    }
}
