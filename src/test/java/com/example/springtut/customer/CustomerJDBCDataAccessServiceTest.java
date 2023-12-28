package com.shreeram800.springtut.customer;
import com.shreeram800.springtut.AbstractTestContainerunitTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerJDBCDataAccessServiceTest extends AbstractTestContainerunitTest{
    private CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper=new CustomerRowMapper();
    @BeforeEach
    void setUp() {
        underTest=new CustomerJDBCDataAccessService(
                getJdbcTemplate(),customerRowMapper);
    }
    @Test
    void selectAllCustomers() {

        Customer customer=new Customer(1,faker.name().fullName(),faker.internet().emailAddress()+ "-" + UUID.randomUUID(),45);
        underTest.insertCustomer(customer);
        List<Customer> customerList=underTest.selectAllCustomers();
        Assertions.assertThat(customerList).isNotEmpty();
    }
    @Test
    void selectCustomerById() {
        String email=faker.internet().emailAddress()+ "-"+ UUID.randomUUID();
        Customer customer=new Customer(faker.name().fullName(),email,45);
        underTest.insertCustomer(customer);
        int id= underTest.selectAllCustomers().stream().filter(c->c.getEmail().equals(email)).map(Customer::getId).findFirst().orElseThrow();
        Optional<Customer> actual =underTest.selectCustomerById(id);
        Assertions.assertThat(actual).isPresent().hasValueSatisfying(c->{
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void insertCustomer() {
        Customer customer=new Customer(faker.name().fullName(),faker.internet().emailAddress()+ "-" + UUID.randomUUID(),45);
        underTest.insertCustomer(customer);
        List<Customer> actual =underTest.selectAllCustomers();
        assertThat(actual.contains(customer));
    }
    @Test
    void existPersonWithEmail() {
        String email=faker.internet().emailAddress()+ "-" + UUID.randomUUID();
        Customer customer=new Customer(faker.name().fullName(),email,45);
        underTest.insertCustomer(customer);
        boolean actual = underTest.existPersonWithEmail(email);
        assertThat(actual).isTrue();
    }

    @Test
    void existPersonWithId() {
        String email = faker.internet().emailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(faker.name().fullName(), email, 45);
        underTest.insertCustomer(customer);
        List<Customer> list = underTest.selectAllCustomers();
        int id = list.stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst().orElseThrow();
        Boolean actual = underTest.existPersonWithId(id);
        assertThat(actual).isTrue();
    }

    @Test
    void deleteCustomerById() {
        String email = faker.internet().emailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(faker.name().fullName(), email, 45);
        underTest.insertCustomer(customer);
        List<Customer> list = underTest.selectAllCustomers();
        int id = list.stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst().orElseThrow();
        underTest.deleteCustomerById(id);
        List<Customer> actual =underTest.selectAllCustomers();
        assertThat(!actual.contains(customer));
    }

    @Test
    void updateNameCustomer() {
        String email = faker.internet().emailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer( faker.name().fullName(), email, 32);
        underTest.insertCustomer(customer);
        List<Customer> list = underTest.selectAllCustomers();
        int id = list.stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        System.out.println(id);
        String updatedName=faker.name().fullName();
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(id);
        updatedCustomer.setName(updatedName);
        updatedCustomer.setEmail(customer.getEmail());
        updatedCustomer.setAge(customer.getAge());
        underTest.updateCustomer(updatedCustomer);
        Optional<Customer> updatedCustomerFromDb = underTest.selectCustomerById(id);
        assertNotNull(updatedCustomerFromDb.orElse(null), "Updated customer not found in the database");
        assertEquals(updatedName, updatedCustomerFromDb.get().getName(), "Name not updated");
        assertEquals(email, updatedCustomerFromDb.get().getEmail(), "Email not updated");
        assertEquals(id, updatedCustomerFromDb.get().getId(), "ID mismatch after update");

    }

    @Test
    void updateEmailCustomer(){
        String email = faker.internet().emailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(faker.name().fullName(),email, 45);
        underTest.insertCustomer(customer);
        List<Customer> list = underTest.selectAllCustomers();
        String finalEmail = email;
        int id = list.stream()
                .filter(c -> c.getEmail().equals(finalEmail))
                .map(Customer::getId)
                .findFirst().orElseThrow();
        var new_email =faker.internet().emailAddress() + "-" + UUID.randomUUID();
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(id);
        updatedCustomer.setEmail(new_email);
        updatedCustomer.setName(customer.getName());
        updatedCustomer.setAge(customer.getAge());
        underTest.updateCustomer(updatedCustomer);
        Optional<Customer> updatedCustomerFromDb = underTest.selectCustomerById(id);
        Assertions.assertThat(updatedCustomerFromDb).isPresent().hasValueSatisfying(c->{
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getEmail()).isEqualTo(new_email);
        });
    }

    @Test
    void updateAgeCustomer() {
        int age = 34;
        String email=faker.internet().emailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(faker.name().fullName(),email, age);
        underTest.insertCustomer(customer);
        List<Customer> list = underTest.selectAllCustomers();
        int id = list.stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst().orElseThrow();
        age = 45;
        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(id);
        updatedCustomer.setAge(age);
        updatedCustomer.setEmail(customer.getEmail());
        updatedCustomer.setName(customer.getName());
        underTest.updateCustomer(updatedCustomer);
        Optional<Customer> updatedCustomerFromDb = underTest.selectCustomerById(id);
        assertNotNull(updatedCustomerFromDb, "Updated customer not found in the database");
        assertEquals(age, updatedCustomerFromDb.get().getAge(), "Age not updated");
    }
}