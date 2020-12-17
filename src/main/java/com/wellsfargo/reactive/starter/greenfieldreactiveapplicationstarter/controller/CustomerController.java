package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.controller;

import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.model.Customer;
import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Customer> create(@RequestBody Customer customer){
        return customerService.createCustomer(customer);
    }

    @GetMapping
    public Flux<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("/{customerId}")
    public Mono<ResponseEntity<Customer>> getUserById(@PathVariable String customerId){
        Mono<Customer> user = customerService.findById(customerId);
        return user.map( u -> ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{customerId}")
    public Mono<ResponseEntity<Void>> deleteByCustomerId(@PathVariable String customerId){
        return customerService.deleteCustomer(customerId)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
