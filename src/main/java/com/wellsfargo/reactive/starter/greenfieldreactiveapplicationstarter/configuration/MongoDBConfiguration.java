package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.configuration;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import java.util.Collection;
import java.util.Collections;

@Configuration
@AllArgsConstructor
public class MongoDBConfiguration extends AbstractReactiveMongoConfiguration {

    private static final String CONNECTION_URL = "mongodb://%s/%s";

    private final MongoProperties mongoProperties;

    @Override
    protected String getDatabaseName() {
        return mongoProperties.getDatabase();
    }

    @Override
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(getMongoDBConnectionUrl());
    }

    @Override
    public Collection getMappingBasePackages() {
        return Collections.singleton("com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter");
    }

    private String getMongoDBConnectionUrl() {
        return String.format(CONNECTION_URL, mongoProperties.getHosts().get(0), mongoProperties.getDatabase());
    }

    @Bean
    ReactiveMongoTransactionManager transactionManager(ReactiveMongoDatabaseFactory reactiveMongoDatabaseFactory) {
        return new ReactiveMongoTransactionManager(reactiveMongoDatabaseFactory);
    }


    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
    }
}
