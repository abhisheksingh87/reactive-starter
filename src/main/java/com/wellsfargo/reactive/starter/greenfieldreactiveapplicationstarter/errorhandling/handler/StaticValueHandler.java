package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.errorhandling.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class StaticValueHandler {

    public Mono<ServerResponse> handleError(ServerRequest request) {
        return findById(request.pathVariable("id"))
                .onErrorReturn("Id is null")
                .flatMap(s -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .bodyValue(s));
    }

    private Mono<String> findById(String id) {
        try {
            return Mono.just(id);
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

}