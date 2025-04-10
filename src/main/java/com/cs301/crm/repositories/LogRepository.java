package com.cs301.crm.repositories;

import com.cs301.crm.models.LogDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface LogRepository extends MongoRepository<LogDocument, UUID> {
    Page<LogDocument> findByActorContainingIgnoreCase(String actor, Pageable pageable);
}
