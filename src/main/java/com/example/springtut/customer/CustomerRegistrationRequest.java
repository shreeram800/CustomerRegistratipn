package com.example.springtut.customer;

public record CustomerRegistrationRequest(
    String name,
    String email,
    Integer age
){
}
