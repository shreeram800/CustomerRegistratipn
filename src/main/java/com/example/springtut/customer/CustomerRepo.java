package com.example.springtut.customer;

import org.springframework.data.jpa.repository.JpaRepository;

// Make sure the Customer class exists and has an Integer primary key
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

}
