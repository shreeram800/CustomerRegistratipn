package com.example.springtut;


import com.example.springtut.customer.Customer;
import com.example.springtut.customer.CustomerRepo;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringTutApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringTutApplication.class, args);
    }
    @Bean
    CommandLineRunner runner(CustomerRepo customerRepo){
      return args -> {
          Faker faker=new Faker();
          Customer customer= new Customer(faker.name().fullName(),faker.internet().emailAddress(),faker.number().numberBetween(18, 90));
          List<Customer> customerList= new ArrayList<>();
          customerList.add(customer);
          customerRepo.saveAll(customerList);
      };
    }
}

