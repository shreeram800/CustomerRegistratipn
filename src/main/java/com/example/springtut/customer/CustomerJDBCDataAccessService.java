package com.shreeram800.springtut.customer;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;
    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql= """
                SELECT * FROM customer
                """;
        return jdbcTemplate.query(sql,customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        var sql = """
                SELECT id, name, email, age
                FROM customer
                WHERE id=?
                """;
        return Optional.ofNullable(jdbcTemplate.<Customer>queryForObject(sql, new Integer[]{id}, customerRowMapper));
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql= """
                INSERT INTO customer(name, email, age)
                VALUES(?,?,?)
                """;
        int result = jdbcTemplate.update(
                sql,customer.getName(),
                customer.getEmail(),
                customer.getAge()
        );
        System.out.println("jdbcTemplate.update = "+result);
    }

    @Override
    public boolean existPersonWithEmail(String email) {
        var sql = """
                SELECT 1 FROM customer
                WHERE email=?
                """;
        try {
            Integer result = jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
            return result!=null && result==1;
        }
        catch (EmptyResultDataAccessException e){
            return false;
        }
    }

    @Override
    public boolean existPersonWithId(Integer id) {
        var sql= """
                SELECT 1 FROM customer 
                WHERE id=?
                """;
        try{
            Integer result = jdbcTemplate.<Integer>queryForObject(sql, new Object[]{id}, Integer.class);
            return result!=null && result==1;
        }
        catch (EmptyResultDataAccessException e){
            return false;
        }
    }

    @Override
    public void deleteCustomerById(Integer id) {
            var sql = """
                    DELETE FROM customer WHERE id=?
                    """;
            jdbcTemplate.update(sql, id);
    }

    @Override
    public void updateCustomer(Customer customer) {
        var sql = """
                UPDATE customer SET name = ?, email = ?, age = ? WHERE id = ?;
                """;
        jdbcTemplate.update(sql, customer.getName(),customer.getEmail(),customer.getAge(),customer.getId());

    }

}
