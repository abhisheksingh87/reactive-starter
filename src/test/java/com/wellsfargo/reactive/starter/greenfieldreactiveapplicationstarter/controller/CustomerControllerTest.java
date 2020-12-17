package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.controller;

import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.GreenfieldReactiveApplication;
import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.model.Account;
import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GreenfieldReactiveApplication.class)
@AutoConfigureWebTestClient
public class CustomerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void createCustomer() {

        Account account = Account.builder().accountNumber(678954L)
                                           .routingNumber(34245L)
                                           .accountOwner("john smith")
                                           .build();
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        Customer customer = Customer.builder().firstName("john")
                                              .lastName("smith")
                                              .phoneNumber("7589756789")
                                              .accounts(accounts).build();

        webTestClient.post().uri("/customer").contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .body(Mono.just(customer),Customer.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.customerId").isNotEmpty()
                .jsonPath("$.firstName").isEqualTo("john");
    }

}
