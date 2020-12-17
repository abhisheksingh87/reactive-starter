package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.repository;

import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.GreenfieldReactiveApplication;
import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.model.Account;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.startsWith;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GreenfieldReactiveApplication.class)
public class AccountRepositoryTest {

    @Autowired
    private AccountMongoRepository repository;

    @Test
    public void testFindById() {
        //given
        Account account = repository.save(new Account(null, 918345L, 234518L, "alex"))
                                    .block();

        //when
        Mono<Account> createdAccount = repository.findById(account.getId());

        //then
        StepVerifier
                .create(createdAccount)
                .assertNext(acc -> {
                    assertThat("alex").isEqualTo(acc.getAccountOwner());
                    assertThat(234518L).isEqualTo(acc.getRoutingNumber());
                    assertThat(acc.getId()).isNotNull();
                })
                .expectComplete()
                .verify();

    }

    @Test
    public void testSave() {
        Mono<Account> accountMono = repository.save(new Account(null, 918345L, 234518L, "alex"));

        StepVerifier
                .create(accountMono)
                .assertNext(account -> assertThat(account.getId()).isNotNull())
                .expectComplete()
                .verify();
    }

    @Test
    public void testFindByAccountOwner() {
        //given
        Account account = repository.save(new Account(null, 918345L, 234518L, "ron"))
                                    .block();

        //when
        Mono<Account> createdAccount = repository.findByAccountOwner(account.getAccountOwner());

        //then
        StepVerifier
                .create(createdAccount)
                .assertNext(acc -> {
                    assertThat("ron").isEqualTo(acc.getAccountOwner());
                    assertThat(234518L).isEqualTo(acc.getRoutingNumber());
                    assertThat(acc.getId()).isNotNull();
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void testFindByAccountNumberAndRoutingNumber() {
        //given
        Account account = repository.save(new Account(null, 918345L, 235189L, "john"))
                                    .block();

        //when
        Mono<Account> createdAccount = repository.findByAccountNumberAndRoutingNumber(account.getAccountNumber(), account.getRoutingNumber());

        //then
        StepVerifier
                .create(createdAccount)
                .assertNext(acc -> {
                    assertThat("john").isEqualTo(acc.getAccountOwner());
                    assertThat(235189L).isEqualTo(acc.getRoutingNumber());
                    assertThat(acc.getId()).isNotNull();
                })
                .expectComplete()
                .verify();
    }

    @Test
    public void testFindAll() {
        //given
        repository.save(new Account(null, 918345L, 234518L, "mike"))
                  .block();
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("accountOwner", startsWith());
        Example<Account> example = Example.of(new Account(null, 918345L, 234518L, "mike"), matcher);

        //when
        Flux<Account> accountFlux = repository.findAll(example);

        //then
        StepVerifier
                .create(accountFlux)
                .assertNext(account -> assertThat("mike").isEqualTo(account.getAccountOwner()))
                .expectComplete()
                .verify();
    }
}