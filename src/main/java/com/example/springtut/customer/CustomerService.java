package com.example.springtut.customer;
import com.example.springtut.exceptions.DuplicateNotFoundException;
import com.example.springtut.exceptions.RequestValidationRespose;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jdbc")CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers(){
        return  customerDao.selectAllCustomers();
    }
    public Customer getCustomer(Integer id){
        return customerDao.selectCustomerById(id).orElseThrow(()->new IllegalArgumentException("Customer with id [%s] not found".formatted(id)));
    }
    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        if(customerDao.existPersonWithEmail(customerRegistrationRequest.email())){
            throw new DuplicateNotFoundException(
                   "email already taken"
            );
        }
        Customer customer =new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age()
        );
        customerDao.insertCustomer(customer);
    }
    public void removeCustomerById(Integer id){
        if(customerDao.existPersonWithId(id)){
           customerDao.deleteCustomerById(id);
        }
        else {
            throw new DuplicateNotFoundException("Customer doesn't exist by id [%d]".formatted(id));
        }
    }
    public void updateCustomer(Integer id, CustomerUpdateRequest request) {
        Customer customer = getCustomer(id);
        boolean update = false;
        if (request.email() != null && !customer.getEmail().equals(request.email())) {
            if (customerDao.existPersonWithEmail(request.email())) {
                throw new DuplicateNotFoundException("email already taken ");
            }
            customer.setEmail(request.email());
            update = true;
        }
        if (request.name() != null && !customer.getName().equals(request.name())) {
            customer.setName(request.name());
            update = true;
        }
        if (request.age() != null && !customer.getAge().equals(request.age())) {
            customer.setAge(request.age());
            update = true;
        }
        if (!update) {
            throw new RequestValidationRespose("No changes required.");
        }
        customerDao.updateCustomer(customer);
    }
}
