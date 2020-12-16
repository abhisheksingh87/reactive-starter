package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.template;

import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.GreenfieldReactiveApplication;
import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GreenfieldReactiveApplication.class)
public class AccountTemplateOperationsTest {

    @Autowired
    private AccountTemplateOperations accountTemplate;

    @Test
    public void testSave() {
        Account account = accountTemplate.save(Mono.just(new Account(null, 918345L, 234518L, "alex"))).block();
        assertThat(account.getId()).isNotNull();
    }

    @Test
    public void testFindById() {
        Mono<Account> accountMono = accountTemplate.save(Mono.just(new Account(null, 918345L, 234518L, "alex")));
        Mono<Account> accountMonoResult = accountTemplate.findById(accountMono.block().getId());
        assertThat(accountMonoResult.block().getId()).isNotNull();
        assertThat(accountMonoResult.block().getAccountOwner()).isEqualTo("alex");
    }

    @Test
    public void testFindAll() {
        Account account1 = accountTemplate.save(Mono.just(new Account(null, 918345L, 234518L, "alex"))).block();
        Account account2 = accountTemplate.save(Mono.just(new Account(null, 918345L, 234518L, "alex"))).block();
        Flux<Account> accountFlux = accountTemplate.findAll();
        List<Account> accounts = accountFlux.collectList().block();
        assertThat(accounts.stream()).anyMatch(x -> account1.getId().equals(x.getId()));
        assertThat(accounts.stream().anyMatch(x -> account2.getId().equals(x.getId())));
    }

}
