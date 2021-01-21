package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.filters;

import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class MDCHeaderFilterTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testHeader() {

        Customer customer = Customer.builder().firstName("john")
                .lastName("smith")
                .phoneNumber("7589756789")
                .build();

       webTestClient.post()
                .uri("/customer")
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .header("MDC-CUSTOMER-ID", "123")
                .body(Mono.just(customer),Customer.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader()
                .exists("MDC-CUSTOMER-ID")
                .expectBody()
                .jsonPath("$.customerId").isNotEmpty()
                .jsonPath("$.firstName").isEqualTo("john");
    }
}