package com.shreeram800.springtut.customer;

public record CustomerRegistrationRequest(
    String name,
    String email,
    Integer age
){
}
