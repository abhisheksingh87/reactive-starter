package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.template;

import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.model.Account;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.ReactiveRemoveOperation;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AccountTemplateOperations {

    private final ReactiveMongoTemplate template;

    public Mono<Account> findById(String id) {
        return template.findById(id, Account.class);
    }

    public Flux<Account> findAll() {
        return template.findAll(Account.class);
    }

    public Mono<Account> save(Mono<Account> account) {
        return template.save(account);
    }

    public ReactiveRemoveOperation.ReactiveRemove<Account> deleteAll() {
        return template.remove(Account.class);
    }
}
