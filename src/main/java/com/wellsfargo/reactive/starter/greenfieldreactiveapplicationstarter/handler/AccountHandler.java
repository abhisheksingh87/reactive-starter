package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.handler;

import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.model.Account;
import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.repository.AccountRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class AccountHandler {

    private final AccountRepository accountRepository;

    public Mono<ServerResponse> getAllAccounts(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountRepository.findAll(), Account.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountRepository.findById(request.pathVariable("id")), Account.class);
    }

    @CircuitBreaker(name = "accountService")
    @Bulkhead(name = "accountService")
    @Retry(name = "accountService")
    public Mono<ServerResponse> findByRoutingNumber(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountRepository.findByRoutingNumber(request.queryParam("routingNumber").get()), Account.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<Account> account = request.bodyToMono(Account.class);
        return account.flatMap(account1 -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(accountRepository.save(account1), Account.class));
    }
}
