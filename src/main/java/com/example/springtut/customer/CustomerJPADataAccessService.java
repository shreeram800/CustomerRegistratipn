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
        return customerRepo.findById(id);
    }
    @Override
    public void insertCustomer(Customer customer) {
        customerRepo.save(customer);
    }
    @Override
    public boolean existPersonWithEmail(String email) {
        return customerRepo.existsCustomerByEmail(email);
    }

    @Override
    public boolean existPersonWithId(Integer id) {
        return customerRepo.existsCustomerById(id);
    }
    @Override
    public void deleteCustomerById(Integer id) {
        customerRepo.deleteById(id);
    }

    @Override
    public void updateCustomer(Customer customer) {
        //customerRepo.deleteAllById(customer.getId());
        customerRepo.save(customer);
    }
}
