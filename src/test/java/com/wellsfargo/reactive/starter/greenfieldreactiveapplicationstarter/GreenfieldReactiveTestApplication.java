package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;

@SpringBootApplication
public class GreenfieldReactiveTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreenfieldReactiveApplication.class, args);
    }

}
