package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GreenfieldReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreenfieldReactiveApplication.class, args);
    }

    @Bean
    public GroupedOpenApi customerOpenApi() {
        String[] paths = { "/customer/**" };
        return GroupedOpenApi.builder().group("customer").pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi accountsOpenApi() {
        String[] paths = { "/account/**" };
        return GroupedOpenApi.builder().group("account").pathsToMatch(paths)
                .build();
    }

}
