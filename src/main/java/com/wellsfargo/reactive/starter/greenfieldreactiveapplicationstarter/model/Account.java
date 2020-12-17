package com.wellsfargo.reactive.starter.greenfieldreactiveapplicationstarter.model;

import lombok.*;
import org.springframework.data.annotation.Id;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Account {

    @Id
    private String id;
    private Long accountNumber;
    private Long routingNumber;
    private String accountOwner;
}
