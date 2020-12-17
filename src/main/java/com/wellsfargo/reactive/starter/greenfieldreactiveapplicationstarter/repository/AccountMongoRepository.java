package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.repository;

import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.model.Account;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AccountMongoRepository extends ReactiveMongoRepository<Account, String> {

    Mono<Account> findByAccountOwner(String accountOwner);

    @Query("{ 'accountNumber': ?0, 'routingNumber': ?1}")
    Mono<Account> findByAccountNumberAndRoutingNumber(Long accountNumber, Long routingNumber);
}
