package com.cs301.crm.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "logs")
@AllArgsConstructor
@Getter
public class LogDocument {

    @Id
    private UUID logId;

    @Indexed(unique = true)
    private String actor;

    @Field(name = "transaction_type")
    private String transactionType;

    private String action;

    private Instant timestamp;
}
