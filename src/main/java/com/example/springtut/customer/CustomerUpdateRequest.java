package com.example.springtut.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
)  {
}
