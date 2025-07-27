package com.AppSenelec.service;

import com.AppSenelec.model.Log;
import com.AppSenelec.repository.LogRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class LogService implements ILogService {
    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public List<Log> findAll() {
        return logRepository.findAll();
    }

    @Override
    public Log save(Log log) {
        return logRepository.save(log);
    }

    @Override
    public Log findById(long id) {
        return logRepository.findById(id).orElse(null);
    }

    @Override
    public List<Log> findAllNotDeleted() {
        return logRepository.findAllNotDeleted();
    }

    @Override
    public void delete(Log log) {
        logRepository.delete(log);
    }

    @Override
    public Log update(long id, Log log) {
        log.setId(id);
        return logRepository.save(log);
    }

    @Override
    public Page<Log> findAllPaginated(Pageable pageable) {
        return logRepository.findAll(pageable);
    }
} 