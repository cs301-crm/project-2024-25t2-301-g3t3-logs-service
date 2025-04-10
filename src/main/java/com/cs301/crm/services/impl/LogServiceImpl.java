package com.cs301.crm.services.impl;

import com.cs301.crm.dtos.responses.GenericResponseDTO;
import com.cs301.crm.dtos.responses.LogResponseDTO;
import com.cs301.crm.models.LogDocument;
import com.cs301.crm.repositories.LogRepository;
import com.cs301.crm.services.LogService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    private final Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);
    private final LogRepository logRepository;

    @Autowired
    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public GenericResponseDTO getLogs(String searchQuery, int page, int limit) {
        Sort sortCriterion = Sort.by(Sort.Direction.DESC, "timestamp");
        Pageable pageable = PageRequest.of(page - 1, limit, sortCriterion);

        logger.info("Request for page {} to {}", page, limit);
        Page<LogDocument> logPages;
        if (searchQuery != null) {
            logPages = logRepository.findByActorContainingIgnoreCase(searchQuery, pageable);
            logger.info("Returning logs for {}", searchQuery);
        } else {
            logPages = logRepository.findAll(pageable);
            logger.info("Returning all logs");
        }

        List<LogResponseDTO> logResponseDTOs = this.generateLogResponseDTOs(logPages);

        return new GenericResponseDTO(
                true,
                logResponseDTOs,
                ZonedDateTime.now()
        );
    }

    private List<LogResponseDTO> generateLogResponseDTOs(Page<LogDocument> logPages) {
        List<LogDocument> logs = logPages.getContent();
        List<LogResponseDTO> logResponseDTOs = new ArrayList<>();
        for (LogDocument log : logs) {
            logResponseDTOs.add(
                    new LogResponseDTO(
                            log.getLogId(),
                            log.getActor(),
                            log.getTransactionType(),
                            log.getAction(),
                            log.getTimestamp()
                    )
            );
        }
        return logResponseDTOs;
    }

    @Override
    public void persistLog(LogDocument logDocument) {
        logRepository.save(logDocument);
        logger.info("Persisted log {}", logDocument);
    }
}
