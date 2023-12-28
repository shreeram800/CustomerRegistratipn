package com.shreeram800.springtut.customer;

import com.shreeram800.springtut.exceptions.DuplicateNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    private CustomerService underTest;
    @Mock
    private CustomerDao customerDao;
    @BeforeEach
    void setUp() {
        underTest=new CustomerService(customerDao);
    }
    @Test
    void getAllCustomers() {
        underTest.getAllCustomers();
        verify(customerDao).selectAllCustomers();
    }
    @Test
    void canGetCustomer() {
        int id=10;
        Customer customer=new Customer(id,"Shree ram","ram@gmail.com",20 );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));
        Customer actual= underTest.getCustomer(id);
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void addCustomer() {
        int id=10;
        String email="krishna@gmail.com";
        when(customerDao.existPersonWithEmail(email)).thenReturn(false);
        CustomerRegistrationRequest registrationRequest= new CustomerRegistrationRequest("Alex",email,20);
        underTest.addCustomer(registrationRequest);
        ArgumentCaptor<Customer>customerArgumentCaptor=ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer =customerArgumentCaptor.getValue();


    }

    @Test
    void wilLThrowWhenCustomerNotFound(){
        int id=10;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(()->underTest.getCustomer(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Customer with id [%s] not found".formatted(id));
    }
    @Test
    void removeCustomerById(){
        int id =10;
        when(customerDao.existPersonWithId(id)).thenReturn(true);

        underTest.removeCustomerById(id);
        verify(customerDao).deleteCustomerById(id);

    }
    @Test
    void willThrowDeleteCustomerNotExist(){
        int id =10;
        when(customerDao.existPersonWithId(id)).thenReturn(false);

        assertThatThrownBy(()->underTest.removeCustomerById(id)).
                isInstanceOf(DuplicateNotFoundException.class)
                .hasMessage("Customer doesn't exist by id [%d]"
                        .formatted(id));

        verify(customerDao,never()).deleteCustomerById(id);
    }

    @Test
    void updateCustomer() {

        int id=10;
        Customer customer=new Customer(id,"Shree ram","ram@gmail.com",20 );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String new_email="krishna@gamil.com";
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("krishna",new_email , 34);
        when(customerDao.existPersonWithEmail(new_email)).thenReturn(false);
        underTest.updateCustomer(id,updateRequest);
        ArgumentCaptor<Customer> customerArgumentCaptor=ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer customer1=customerArgumentCaptor.getValue();
        assertThat(customer1.getName()).isEqualTo(updateRequest.name());
        assertThat(customer1.getEmail()).isEqualTo(updateRequest.email());
        assertThat(customer1.getAge()).isEqualTo(updateRequest.age());

    }
}