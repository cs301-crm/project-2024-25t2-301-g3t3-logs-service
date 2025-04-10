package com.cs301.crm.consumers;

import com.cs301.crm.mapper.LogMapper;
import com.cs301.crm.services.LogService;
import com.cs301.shared.protobuf.Log;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class LogConsumer {
    private final Logger logger = LoggerFactory.getLogger(LogConsumer.class);

    private final LogService logService;
    private final LogMapper logMapper;
    
    @Autowired
    public LogConsumer(LogService logService, LogMapper logMapper) {
        this.logService = logService;
        this.logMapper = logMapper;
    }

    @KafkaListener(topics = "logs",
            id = "log-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeLog(ConsumerRecord<String, Log> record) {
        logger.info("Consumed log from kafka with offset {}", record.offset());
        logService.persistLog(logMapper.LogProtobufToModel(record));
    }

}
