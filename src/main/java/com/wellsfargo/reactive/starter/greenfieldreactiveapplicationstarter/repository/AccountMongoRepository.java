package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.repository;

import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.model.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccountMongoRepository extends ReactiveMongoRepository<Account, String> {
}
