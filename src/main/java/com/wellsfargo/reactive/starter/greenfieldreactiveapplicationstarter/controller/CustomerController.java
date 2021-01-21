package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.controller;

import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.helper.LogHelper;
import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.model.Customer;
import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.helper.LogHelper.logOnNext;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
@Slf4j
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
    public Mono<Customer> getCustomerById(@PathVariable String customerId){
        return customerService.findById(customerId)
                .doOnEach(logOnNext(customer -> log.info("Customer: {}", customer)))
                .subscriberContext(LogHelper.put("CUSTOMER-ID", customerId));
    }

    @DeleteMapping("/{customerId}")
    public Mono<ResponseEntity<Void>> deleteByCustomerId(@PathVariable String customerId){
        return customerService.deleteCustomer(customerId)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
