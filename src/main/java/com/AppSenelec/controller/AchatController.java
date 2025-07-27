package com.AppSenelec.controller;

import com.AppSenelec.dto.CreateAchatRequest;
import com.AppSenelec.dto.CreateAchatResponse;
import com.AppSenelec.helper.AchatHelper;
import com.AppSenelec.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/achats")
public class AchatController {
    private final AchatHelper achatHelper;

    public AchatController(AchatHelper achatHelper) {
        this.achatHelper = achatHelper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateAchatResponse>> save(@RequestBody @Valid CreateAchatRequest request) {
        ApiResponse<CreateAchatResponse> response = achatHelper.save(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CreateAchatResponse>>> allAchats(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        ApiResponse<List<CreateAchatResponse>> response = achatHelper.findAll(page, size, sortBy, sortDir);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CreateAchatResponse>> getById(@PathVariable long id) {
        ApiResponse<CreateAchatResponse> response = achatHelper.findById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CreateAchatResponse>> update(@PathVariable long id, @RequestBody @Valid CreateAchatRequest request) {
        ApiResponse<CreateAchatResponse> response = achatHelper.update(id, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable long id) {
        ApiResponse<String> response = achatHelper.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
} 