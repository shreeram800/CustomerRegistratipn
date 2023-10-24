package com.example.springtut.customer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccess implements CustomerDao{

    private static final List<Customer> customers;

    static {
        customers=new ArrayList<>();
        Customer alex= new Customer(1,"alex","jcnsjcndjkc",21);
        customers.add(alex);

    }
    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return customers.stream().filter(c ->c.getId().equals(id)).findFirst();
    }
}
