package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class BlockingService {

    public Mono<Integer> blockingIsAllowed() {
        return getBlockingMono().subscribeOn(Schedulers.elastic());
    }

    public Mono<Integer> blockingIsNotAllowed() {
        return getBlockingMono().subscribeOn(Schedulers.parallel());
    }

    private Mono<Integer> getBlockingMono() {
        return Mono.just(1).doOnNext(i -> block());
    }

    private void block() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
