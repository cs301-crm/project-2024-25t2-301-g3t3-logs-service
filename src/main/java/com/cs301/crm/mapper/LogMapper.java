package com.cs301.crm.mapper;

import com.cs301.crm.models.LogDocument;
import com.cs301.shared.protobuf.Log;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class LogMapper {
    private static final Logger logger = LoggerFactory.getLogger(LogMapper.class);

    public LogDocument LogProtobufToModel(ConsumerRecord<String, Log> record) {
        Log logMessage = record.value();

        return new LogDocument(
                UUID.fromString(logMessage.getLogId()),
                logMessage.getActor(),
                logMessage.getTransactionType(),
                logMessage.getAction(),
                Instant.parse(logMessage.getTimestamp())
        );
    }
}
