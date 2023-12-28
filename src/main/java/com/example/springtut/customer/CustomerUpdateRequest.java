package com.shreeram800.springtut.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
)  {
}
