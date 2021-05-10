package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.service;

import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.GreenfieldReactiveApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GreenfieldReactiveApplication.class)
public class BlockingServiceTest {

    @Autowired
    private BlockingService blockingService;

    @Test
    void blockingInNonBlockingThreadsShouldNotBeAllowed() {
        StepVerifier
                .create(blockingService.blockingIsNotAllowed())
                .expectErrorMatches(e -> {
                    e.printStackTrace();

                    return e instanceof Error &&
                            e.getMessage().contains("Blocking call!");
                })
                .verify();
    }

    @Test
    void blockingInBlockingThreadsShouldBeAllowed() {
        StepVerifier
                .create(blockingService.blockingIsAllowed())
                .expectNext(1)
                .verifyComplete();
    }
}
