package com.shreeram800.springtut.journey;

import com.shreeram800.springtut.customer.Customer;
import com.shreeram800.springtut.customer.CustomerRegistrationRequest;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIT {

    @Autowired
    private WebTestClient webTestClient;
    private static final Random RANDOM=new Random();

    private static final String uri="/api/vi/customers";
    @Test
    void canRegisterCustomer(){
        Faker faker=new Faker();
        Name fakerName=faker.name();
        String name=fakerName.fullName();
        String email=fakerName.lastName()+ UUID.randomUUID()+"@foob656am.com";
        int age=RANDOM.nextInt(1,100);

        CustomerRegistrationRequest request=new CustomerRegistrationRequest(
                name,email,age
        );

        webTestClient.post().uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request),CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        List<Customer> customers = webTestClient.get()
                .uri(uri).accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>(){})
                .returnResult()
                .getResponseBody();

        Customer expected=new Customer(
                name,
                email,
                age
        );
        assertThat(customers).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expected);

        assert customers != null;
        int id= customers.stream().filter(customer -> customer
                .getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        expected.setId(id);

        webTestClient.get()
                .uri(uri+"/{id}",id).accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>(){})
                .isEqualTo(expected);
    }
    @Test
    void canDeleteCustomer(){
        Faker faker=new Faker();
        Name fakerName=faker.name();
        String name=fakerName.fullName();
        String email=fakerName.lastName()+ UUID.randomUUID()+"@foob656am.com";
        int age=RANDOM.nextInt(1,100);

        CustomerRegistrationRequest request=new CustomerRegistrationRequest(
                name,email,age
        );
        webTestClient.post().uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request),CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        List<Customer> customers = webTestClient.get()
                .uri(uri).accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>(){})
                .returnResult()
                .getResponseBody();


        assert customers != null;
        int id= customers.stream().filter(customer -> customer.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        System.out.println(id);
        webTestClient.delete()
                .uri(uri + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void canUpdateCustomer() {
        Faker faker=new Faker();
        Name fakerName=faker.name();
        String name=fakerName.fullName();
        String email=fakerName.lastName()+ UUID.randomUUID()+"@foob656am.com";
        int age=RANDOM.nextInt(1,100);
        CustomerRegistrationRequest request=new CustomerRegistrationRequest(
                name,email,age
        );

        webTestClient.post().uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request),CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        List<Customer> customers = webTestClient.get()
                .uri(uri).accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>(){})
                .returnResult()
                .getResponseBody();

        Customer expected=new Customer(
                name,
                email,
                age
        );
        assertThat(customers).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expected);
        assert customers != null;
        int id= customers.stream().filter(customer -> customer.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        expected.setId(id);

        webTestClient.get()
                .uri(uri+"/{id}",id).accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>(){})
                .isEqualTo(expected);
    }
}
