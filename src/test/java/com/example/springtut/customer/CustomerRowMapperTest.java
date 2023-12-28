package com.shreeram800.springtut.customer;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        CustomerRowMapper customerRowMapper=new CustomerRowMapper();
        ResultSet resultSet=mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("age")).thenReturn(19);
        when(resultSet.getString("name")).thenReturn("krishna");
        when(resultSet.getString("email")).thenReturn("krishna@gmail.com");
        Customer customer=customerRowMapper.mapRow(resultSet,1);
        Customer expected =new Customer(1,"krishna","krishna@gmail.com",19);
        assertThat(customer);
    }
}