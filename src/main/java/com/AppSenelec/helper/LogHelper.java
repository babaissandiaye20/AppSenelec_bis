package com.AppSenelec.helper;

import com.AppSenelec.dto.CreateLogRequest;
import com.AppSenelec.dto.CreateLogResponse;
import com.AppSenelec.exception.NotFoundException;
import com.AppSenelec.mapper.LogMapper;
import com.AppSenelec.model.Log;
import com.AppSenelec.response.ApiResponse;
import com.AppSenelec.service.ILogService;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Component
public class LogHelper {
    private final ILogService logService;

    public LogHelper(ILogService logService) {
        this.logService = logService;
    }

    public ApiResponse<CreateLogResponse> save(CreateLogRequest request) {
        Log log = LogMapper.toEntity(request);
        log = logService.save(log);
        CreateLogResponse response = LogMapper.toResponse(log);
        return ApiResponse.<CreateLogResponse>builder()
                .message("Log créé avec succès")
                .date(LocalDateTime.now())
                .status(201)
                .data(response)
                .build();
    }

    public ApiResponse<CreateLogResponse> findById(long id) {
        Log log = logService.findById(id);
        if (log == null) {
            throw new NotFoundException("Log avec id " + id + " introuvable");
        }
        CreateLogResponse response = LogMapper.toResponse(log);
        return ApiResponse.<CreateLogResponse>builder()
                .message("Log trouvé")
                .date(LocalDateTime.now())
                .status(200)
                .data(response)
                .build();
    }

    public ApiResponse<List<CreateLogResponse>> findAll(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : 
            Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Log> logsPage = logService.findAllPaginated(pageable);
        
        List<CreateLogResponse> list = logsPage.getContent().stream()
                .map(LogMapper::toResponse)
                .collect(Collectors.toList());
        
        return ApiResponse.<List<CreateLogResponse>>builder()
                .message("Liste des logs récupérée avec succès")
                .date(LocalDateTime.now())
                .status(200)
                .data(list)
                .totalElements(logsPage.getTotalElements())
                .totalPages(logsPage.getTotalPages())
                .currentPage(logsPage.getNumber())
                .pageSize(logsPage.getSize())
                .isFirst(logsPage.isFirst())
                .isLast(logsPage.isLast())
                .build();
    }

    public ApiResponse<CreateLogResponse> update(long id, CreateLogRequest request) {
        Log existing = logService.findById(id);
        if (existing == null) {
            throw new NotFoundException("Log avec id " + id + " introuvable");
        }
        Log updated = LogMapper.toEntity(request);
        updated.setId(id);
        updated = logService.update(id, updated);
        CreateLogResponse response = LogMapper.toResponse(updated);
        return ApiResponse.<CreateLogResponse>builder()
                .message("Log mis à jour avec succès")
                .date(LocalDateTime.now())
                .status(200)
                .data(response)
                .build();
    }

    public ApiResponse<String> delete(long id) {
        Log log = logService.findById(id);
        if (log == null) {
            throw new NotFoundException("Log avec id " + id + " introuvable");
        }
        logService.delete(log);
        return ApiResponse.<String>builder()
                .message("Log supprimé avec succès")
                .date(LocalDateTime.now())
                .status(200)
                .data("Log supprimé")
                .build();
    }
} 