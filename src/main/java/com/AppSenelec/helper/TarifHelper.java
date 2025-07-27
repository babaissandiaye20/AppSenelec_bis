package com.AppSenelec.helper;

import com.AppSenelec.dto.CreateTarifRequest;
import com.AppSenelec.dto.CreateTarifResponse;
import com.AppSenelec.exception.NotFoundException;
import com.AppSenelec.mapper.TarifMapper;
import com.AppSenelec.model.Tarif;
import com.AppSenelec.response.ApiResponse;
import com.AppSenelec.service.ITarifService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TarifHelper {
    private final ITarifService tarifService;

    public TarifHelper(ITarifService tarifService) {
        this.tarifService = tarifService;
    }

    public ApiResponse<CreateTarifResponse> save(CreateTarifRequest request) {
        Tarif tarif = TarifMapper.toEntity(request);
        tarif = tarifService.save(tarif);
        CreateTarifResponse response = TarifMapper.toResponse(tarif);
        return ApiResponse.<CreateTarifResponse>builder()
                .message("Tarif créé avec succès")
                .date(LocalDateTime.now())
                .status(201)
                .data(response)
                .build();
    }

    public ApiResponse<CreateTarifResponse> findById(long id) {
        Tarif tarif = tarifService.findById(id);
        if (tarif == null) {
            throw new NotFoundException("Tarif avec id " + id + " introuvable");
        }
        CreateTarifResponse response = TarifMapper.toResponse(tarif);
        return ApiResponse.<CreateTarifResponse>builder()
                .message("Tarif trouvé")
                .date(LocalDateTime.now())
                .status(200)
                .data(response)
                .build();
    }

    public ApiResponse<List<CreateTarifResponse>> findAll(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : 
            Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Tarif> tarifsPage = tarifService.findAllPaginated(pageable);
        
        List<CreateTarifResponse> list = tarifsPage.getContent().stream()
                .map(TarifMapper::toResponse)
                .collect(Collectors.toList());
        
        return ApiResponse.<List<CreateTarifResponse>>builder()
                .message("Liste des tarifs récupérée avec succès")
                .date(LocalDateTime.now())
                .status(200)
                .data(list)
                .totalElements(tarifsPage.getTotalElements())
                .totalPages(tarifsPage.getTotalPages())
                .currentPage(tarifsPage.getNumber())
                .pageSize(tarifsPage.getSize())
                .isFirst(tarifsPage.isFirst())
                .isLast(tarifsPage.isLast())
                .build();
    }

    public ApiResponse<CreateTarifResponse> update(long id, CreateTarifRequest request) {
        Tarif existing = tarifService.findById(id);
        if (existing == null) {
            throw new NotFoundException("Tarif avec id " + id + " introuvable");
        }
        Tarif updated = TarifMapper.toEntity(request);
        updated.setId(id);
        updated = tarifService.update(id, updated);
        CreateTarifResponse response = TarifMapper.toResponse(updated);
        return ApiResponse.<CreateTarifResponse>builder()
                .message("Tarif mis à jour avec succès")
                .date(LocalDateTime.now())
                .status(200)
                .data(response)
                .build();
    }

    public ApiResponse<String> delete(long id) {
        Tarif tarif = tarifService.findById(id);
        if (tarif == null) {
            throw new NotFoundException("Tarif avec id " + id + " introuvable");
        }
        tarifService.delete(tarif);
        return ApiResponse.<String>builder()
                .message("Tarif supprimé avec succès")
                .date(LocalDateTime.now())
                .status(200)
                .data("Tarif supprimé")
                .build();
    }
} 