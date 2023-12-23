package com.example.springtut.customer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vi/customers")
public class CustomerController{
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping( method = RequestMethod.GET)
    public List<Customer> getCustomers(){
        return customerService.getAllCustomers();
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Customer getCustomer(@PathVariable("id")Integer id) {
        return customerService.getCustomer(id);
    }
    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest request){
        customerService.addCustomer(request);
    }
    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable("id") Integer id){
        customerService.removeCustomerById(id);
    }

    @PutMapping("/{id}")
    public void updateCustomerById(@PathVariable("id") Integer id , @RequestBody CustomerUpdateRequest request){
        customerService.updateCustomer(id,request);
    }
}
