package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

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
