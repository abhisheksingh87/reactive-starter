package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.repository;

import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.GreenfieldReactiveApplication;
import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.model.Account;
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
        Account inserted = repository.save(new Account(null, 918345L, 234518L, "alex"))
                                    .block();

        //when
        Mono<Account> accountMono = repository.findById(inserted.getId());

        //then
        StepVerifier
                .create(accountMono)
                .assertNext(account -> {
                    assertThat("alex").isEqualTo(account.getAccountOwner());
                    assertThat(234518L).isEqualTo(account.getRoutingNumber());
                    assertThat(account.getId()).isNotNull();
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
    public void testFindAll() {
        //given
        repository.save(new Account(null, 918345L, 234518L, "alex"))
                  .block();
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("accountOwner", startsWith());
        Example<Account> example = Example.of(new Account(null, 918345L, 234518L, "alex"), matcher);

        //when
        Flux<Account> accountFlux = repository.findAll(example);

        //then
        StepVerifier
                .create(accountFlux)
                .assertNext(account -> assertThat("alex").isEqualTo(account.getAccountOwner()))
                .expectComplete()
                .verify();
    }
}