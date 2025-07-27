package com.AppSenelec.service;

import com.AppSenelec.model.Log;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ILogService {
    List<Log> findAll();
    Log save(Log log);
    Log findById(long id);
    List<Log> findAllNotDeleted();
    void delete(Log log);
    Log update(long id, Log log);
    Page<Log> findAllPaginated(Pageable pageable);
} 