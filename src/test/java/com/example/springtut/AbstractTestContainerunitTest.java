package com.example.springtut;

import com.github.javafaker.Faker;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
@Testcontainers
public class AbstractTestContainerunitTest {

    @BeforeAll
    static void beforeAll() {
        Flyway flyway=Flyway.configure().dataSource(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        ).load();
        flyway.migrate();
    }

    @Container
    protected static final PostgreSQLContainer<?> postgres=
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("ram-dao")
                    .withUsername("shreedubey")
                    .withPassword("passs")
                    .withReuse(true);

    @DynamicPropertySource
    private static void registerDataSource(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url",
                postgres::getJdbcUrl
        );
        registry.add("spring.datasource.url",
                postgres::getUsername
        );
        registry.add("spring.datasource.url",
                postgres::getPassword
        );
    }

     private static DataSource getDataSource(){
        DataSourceBuilder<?> builder = DataSourceBuilder.create().driverClassName(postgres.getDriverClassName())
                .url(postgres.getJdbcUrl())
                .password(postgres.getPassword())
                .username(postgres.getUsername());
        return builder.build();
    }
    protected static JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(getDataSource());
    }

    protected static final Faker faker= new Faker();

}
