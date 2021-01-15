package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.service;

import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.model.Customer;
import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.repository.CustomerRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class CustomerService {

    private static final String CUSTOMER_SERVICE = "customerService";

    private final CustomerRepository customerRepository;

    @CircuitBreaker(name = CUSTOMER_SERVICE)
 //   @Retry(name = CUSTOMER_SERVICE)
 //   @Bulkhead(name = CUSTOMER_SERVICE)
    public Flux<Customer> getAllCustomers() {
        return customerRepository.findAll()
                .switchIfEmpty(Flux.error(new IOException("Customer Not Found")));
    }

    @CircuitBreaker(name = CUSTOMER_SERVICE)
    @Bulkhead(name = CUSTOMER_SERVICE)
    public Mono<Customer> findById(String customerId) {
        return customerRepository.findById(customerId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NO_CONTENT, "Customer Not Found")));
    }

    public Mono<Customer> createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Mono<Customer> deleteCustomer(String customerId) {
        return customerRepository.findById(customerId)
                .flatMap(existingUser -> customerRepository.delete(existingUser)
                        .then(Mono.just(existingUser)));
    }

    public Mono<Customer> fallback(Exception ex) {
        log.info("In Fallback");
        return Mono.just(Customer.builder().build());
    }

}
