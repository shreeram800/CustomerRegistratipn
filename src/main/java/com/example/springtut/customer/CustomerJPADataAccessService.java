package com.example.springtut.customer;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository("jpa")
public class CustomerJPADataAccessService implements CustomerDao {
    private final CustomerRepo customerRepo;

    public CustomerJPADataAccessService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }
    @Override
    public List<Customer> selectAllCustomers() {
        return customerRepo.findAll();
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return Optional.empty();
    }
}
