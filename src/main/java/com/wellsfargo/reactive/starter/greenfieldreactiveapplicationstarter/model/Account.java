package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    private String id;
    private Long accountNumber;
    private Long routingNumber;
    private String accountOwner;
}
