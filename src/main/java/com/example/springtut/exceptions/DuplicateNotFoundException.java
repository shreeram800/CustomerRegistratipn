package com.example.springtut.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class DuplicateNotFoundException extends RuntimeException{
    public DuplicateNotFoundException(String message){
        super(message);
    }

}
