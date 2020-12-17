package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.repository;

import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.model.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
}
