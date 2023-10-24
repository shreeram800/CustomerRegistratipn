package com.example.springtut;


import com.example.springtut.customer.Customer;
import com.example.springtut.customer.CustomerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;

@SpringBootApplication
public class SpringTutApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringTutApplication.class, args);
    }
    CommandLineRunner runner(CustomerRepo customerRepo){
      return args -> {
          Customer alex= new Customer("alex","jcnsjcndjkc",21);
          Customer jhon= new Customer("jhon","jcdbdjcbcb",23);
          List<Customer> customerList= List.of(alex,jhon);
          customerRepo.saveAll(customerList);
      };
    }
}

