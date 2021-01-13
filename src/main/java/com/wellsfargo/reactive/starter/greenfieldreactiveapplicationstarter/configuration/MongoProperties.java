package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties("mongo")
@Component
@Data
public class MongoProperties {
    private List<String> hosts;
    private String database;
}
