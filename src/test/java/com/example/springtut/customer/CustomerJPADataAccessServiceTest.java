package com.shreeram800.springtut.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;


class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private CustomerRepo customerRepo;
    @BeforeEach
    void setUp() {
        autoCloseable=MockitoAnnotations.openMocks(this);
        underTest=new CustomerJPADataAccessService(customerRepo);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        underTest.selectAllCustomers();
        verify(customerRepo).findAll();
    }

    @Test
    void selectCustomerById() {
        int id=1;
        underTest.selectCustomerById(id);
        verify(customerRepo).findById(id);

    }

    @Test
    void insertCustomer() {

    }

    @Test
    void existPersonWithEmail() {
    }

    @Test
    void existPersonWithId() {
    }

    @Test
    void deleteCustomerById() {
    }

    @Test
    void updateCustomer() {
    }
}