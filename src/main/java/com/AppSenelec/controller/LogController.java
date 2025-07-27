package com.AppSenelec.controller;

import com.AppSenelec.dto.CreateLogRequest;
import com.AppSenelec.dto.CreateLogResponse;
import com.AppSenelec.helper.LogHelper;
import com.AppSenelec.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {
    private final LogHelper logHelper;

    public LogController(LogHelper logHelper) {
        this.logHelper = logHelper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateLogResponse>> save(@RequestBody @Valid CreateLogRequest request) {
        ApiResponse<CreateLogResponse> response = logHelper.save(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CreateLogResponse>>> allLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        ApiResponse<List<CreateLogResponse>> response = logHelper.findAll(page, size, sortBy, sortDir);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CreateLogResponse>> getById(@PathVariable long id) {
        ApiResponse<CreateLogResponse> response = logHelper.findById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CreateLogResponse>> update(@PathVariable long id, @RequestBody @Valid CreateLogRequest request) {
        ApiResponse<CreateLogResponse> response = logHelper.update(id, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable long id) {
        ApiResponse<String> response = logHelper.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
} 