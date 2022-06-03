package com.example.catering.exceptions;

public class InsertErrorException extends RuntimeException{
    public InsertErrorException(String errorMsg) {
        super(errorMsg);
    }
}
