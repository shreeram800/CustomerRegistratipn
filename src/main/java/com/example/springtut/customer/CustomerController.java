package com.example.springtut.customer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class CustomerController{
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "api/vi/customer", method = RequestMethod.GET)
    public List<Customer> getCustomers(){
        return customerService.getAllCustomers();
    }
    @RequestMapping(value = "api/vi/customer/{id}", method = RequestMethod.GET)
    public Customer getCustomer(@PathVariable("id")Integer id){
        return customerService.getCustomer(id);
    }
}
