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
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GreenfieldReactiveApplication.class)
public class AccountTemplateOperationsTest {

    @Autowired
    private AccountTemplateOperations accountTemplate;

    @Test
    public void testSave() {
        //when
        Account account = accountTemplate.save(Mono.just(new Account(null, "918345", "234518", "alex"))).block();

        //then
        assertThat(account.getId()).isNotNull();
    }

    @Test
    public void testFindById() {
        //given
        Mono<Account> accountMono = accountTemplate.save(Mono.just(new Account(null, "918345", "234518", "alex")));

        //when
        Mono<Account> accountMonoResult = accountTemplate.findById(accountMono.block().getId());

        //then
        assertThat(accountMonoResult.block().getId()).isNotNull();
        assertThat(accountMonoResult.block().getAccountOwner()).isEqualTo("alex");
    }

    @Test
    public void testFindAll() {
        //given
        Account account1 = accountTemplate.save(Mono.just(new Account(null, "918345", "234518", "alex"))).block();
        Account account2 = accountTemplate.save(Mono.just(new Account(null, "918345", "234518", "alex"))).block();

        //when
        Flux<Account> accountFlux = accountTemplate.findAll();
        List<Account> accounts = accountFlux.collectList().block();

        //then
        assertThat(accounts.stream()).anyMatch(x -> account1.getId().equals(x.getId()));
        assertThat(accounts.stream().anyMatch(x -> account2.getId().equals(x.getId())));
    }

    @Test
    public void testFindByAccountNumberAndRoutingNumber() {
        //given
        Account account = accountTemplate.save(Mono.just(new Account(null, "9183345L", "2334518", "john"))).block();

        //when
        Flux<Account> createdAccount = accountTemplate.findByAccountNumberAndRoutingNumber(account.getAccountNumber(), account.getRoutingNumber());

        //then
        StepVerifier
                .create(createdAccount)
                .assertNext(acc -> {
                    assertThat("john").isEqualTo(acc.getAccountOwner());
                    assertThat(2334518L).isEqualTo(acc.getRoutingNumber());
                    assertThat(acc.getId()).isNotNull();
                })
                .expectComplete()
                .verify();
    }

}
