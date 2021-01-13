package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.router;

import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.handler.AccountHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AccountRouter {

    @Bean
    public RouterFunction<ServerResponse> accountsRoute(AccountHandler accountHandler) {
        return route(GET("/accounts").and(accept(MediaType.APPLICATION_JSON)), accountHandler::getAllAccounts)

              .and(route(GET("/accounts/{id}").and(accept(MediaType.APPLICATION_JSON)), accountHandler::findById))

              .and(route(POST("/accounts").and(accept(MediaType.APPLICATION_JSON)), accountHandler::save));
    }

    @Bean
    public RouterFunction<ServerResponse> accountsQueryRoute(AccountHandler accountHandler) {
        return RouterFunctions.route()
                .GET("/accounts", RequestPredicates.queryParam("routingNumber", t -> true), accountHandler::findByRoutingNumber)
                .build();
    }
}
