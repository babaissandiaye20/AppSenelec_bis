package com.AppSenelec.controller;

import com.AppSenelec.dto.CreateCompteurRequest;
import com.AppSenelec.dto.CreateCompteurResponse;
import com.AppSenelec.helper.CompteurHelper;
import com.AppSenelec.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

class RechargeRequest {
    public String codeRecharge;
    public String referenceCompteur;
}

@RestController
@RequestMapping("/api/compteurs")
public class CompteurController {
    private final CompteurHelper compteurHelper;

    public CompteurController(CompteurHelper compteurHelper) {
        this.compteurHelper = compteurHelper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CreateCompteurResponse>> save(@RequestBody @Valid CreateCompteurRequest request) {
        ApiResponse<CreateCompteurResponse> response = compteurHelper.save(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/recharge")
    public ResponseEntity<ApiResponse<CreateCompteurResponse>> rechargeCompteur(@RequestBody RechargeRequest request) {
        ApiResponse<CreateCompteurResponse> response = compteurHelper.rechargeCompteur(request.codeRecharge, request.referenceCompteur);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CreateCompteurResponse>>> allCompteurs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        ApiResponse<List<CreateCompteurResponse>> response = compteurHelper.findAll(page, size, sortBy, sortDir);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CreateCompteurResponse>> getById(@PathVariable long id) {
        ApiResponse<CreateCompteurResponse> response = compteurHelper.findById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CreateCompteurResponse>> update(@PathVariable long id, @RequestBody @Valid CreateCompteurRequest request) {
        ApiResponse<CreateCompteurResponse> response = compteurHelper.update(id, request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable long id) {
        ApiResponse<String> response = compteurHelper.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
} 