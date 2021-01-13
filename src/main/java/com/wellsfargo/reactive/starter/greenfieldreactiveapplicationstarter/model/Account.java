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
    private String accountNumber;
    private String routingNumber;
    private String accountOwner;
}
