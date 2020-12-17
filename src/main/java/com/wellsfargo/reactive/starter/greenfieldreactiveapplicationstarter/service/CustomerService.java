package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.service;

import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.model.Customer;
import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.repository.CustomerRepository;
import io.netty.util.AsyncMapping;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CustomerService {


    private CustomerRepository customerRepository;


    public Mono<Customer> createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Flux<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Mono<Customer> findById(String customerId) {
        return customerRepository.findById(customerId);
    }

    public Mono<Customer> deleteCustomer(String customerId) {
        return customerRepository.findById(customerId)
                .flatMap(existingUser -> customerRepository.delete(existingUser)
                        .then(Mono.just(existingUser)));
    }
}
