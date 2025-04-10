package com.cs301.crm.dtos.responses;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.UUID;

public record LogResponseDTO(
        UUID logId,

        String actor,

        String transactionType,

        String action,

        Instant timestamp
) {
}
