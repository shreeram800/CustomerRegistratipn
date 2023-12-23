package com.example.springtut.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.BAD_REQUEST)
public class RequestValidationRespose extends RuntimeException{
    public RequestValidationRespose(String message){
        super(message);
    }
}
