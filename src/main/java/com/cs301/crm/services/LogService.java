package com.cs301.crm.services;

import com.cs301.crm.dtos.responses.GenericResponseDTO;
import com.cs301.crm.models.LogDocument;

public interface LogService {
    GenericResponseDTO getLogs(String searchQuery, int page, int limit);
    void persistLog(LogDocument log);
}
