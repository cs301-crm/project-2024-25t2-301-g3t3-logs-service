package com.cs301.crm.consumers;

import com.cs301.crm.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class LogConsumer {
    
    private final LogRepository logRepository;
    
    @Autowired
    public LogConsumer(LogRepository logRepository) {
        this.logRepository = logRepository;
    }
    
    
}
