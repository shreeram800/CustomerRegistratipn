package com.example.springtut.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    Boolean existsCustomerByEmail(String email);
    Boolean existsCustomerById(Integer id);
}
