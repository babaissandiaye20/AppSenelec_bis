package com.AppSenelec.controller;

import com.AppSenelec.dto.CreateTarifRequest;
import com.AppSenelec.dto.CreateTarifResponse;
import com.AppSenelec.helper.TarifHelper;
import com.AppSenelec.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tarifs")
public class TarifController {
    private final TarifHelper tarifHelper;

    public TarifController(TarifHelper tarifHelper) {
        this.tarifHelper = tarifHelper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateTarifResponse>> save(@RequestBody @Valid CreateTarifRequest request) {
        ApiResponse<CreateTarifResponse> response = tarifHelper.save(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CreateTarifResponse>>> allTarifs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        ApiResponse<List<CreateTarifResponse>> response = tarifHelper.findAll(page, size, sortBy, sortDir);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CreateTarifResponse>> getById(@PathVariable long id) {
        ApiResponse<CreateTarifResponse> response = tarifHelper.findById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CreateTarifResponse>> update(@PathVariable long id, @RequestBody @Valid CreateTarifRequest request) {
        ApiResponse<CreateTarifResponse> response = tarifHelper.update(id, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable long id) {
        ApiResponse<String> response = tarifHelper.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
} 