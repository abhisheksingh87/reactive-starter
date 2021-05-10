package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.errorhandling.router;

import com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.errorhandling.handler.StaticValueHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class ErrorRouter {

    @Bean
    public RouterFunction<ServerResponse> staticValueRouter(StaticValueHandler staticValueHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/api/errorReturn")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), staticValueHandler::handleError);
    }
}