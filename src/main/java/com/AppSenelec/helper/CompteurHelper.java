package com.AppSenelec.helper;

import com.AppSenelec.dto.CreateCompteurRequest;
import com.AppSenelec.dto.CreateCompteurResponse;
import com.AppSenelec.exception.NotFoundException;
import com.AppSenelec.mapper.CompteurMapper;
import com.AppSenelec.model.Compteur;
import com.AppSenelec.repository.CompteurRepository;
import com.AppSenelec.response.ApiResponse;
import com.AppSenelec.service.ICompteurService;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Component
public class CompteurHelper {
    private final ICompteurService compteurService;

    public CompteurHelper(ICompteurService compteurService) {
        this.compteurService = compteurService;
    }

    public ApiResponse<CreateCompteurResponse> save(CreateCompteurRequest request) {
        // Vérifier l'unicité de la référence
        if (compteurService.existsByReferenceNumber(request.getReferenceNumber())) {
            throw new IllegalArgumentException("Un compteur avec la référence " + request.getReferenceNumber() + " existe déjà");
        }
        
        Compteur compteur = CompteurMapper.toEntity(request);
        if (compteur.getKwhConsumption() == null) {
            compteur.setKwhConsumption(0.0);
        }
        compteur.setCreationDate(LocalDateTime.now());
        compteur.setStatus("ACTIVE");
        compteur = compteurService.save(compteur);
        CreateCompteurResponse response = CompteurMapper.toResponse(compteur);
        return ApiResponse.<CreateCompteurResponse>builder()
                .message("Compteur créé avec succès")
                .date(LocalDateTime.now())
                .status(201)
                .data(response)
                .build();
    }

    public ApiResponse<CreateCompteurResponse> findById(long id) {
        Compteur compteur = compteurService.findById(id);
        if (compteur == null) {
            throw new NotFoundException("Compteur avec id " + id + " introuvable");
        }
        CreateCompteurResponse response = CompteurMapper.toResponse(compteur);
        return ApiResponse.<CreateCompteurResponse>builder()
                .message("Compteur trouvé")
                .date(LocalDateTime.now())
                .status(200)
                .data(response)
                .build();
    }

    public ApiResponse<List<CreateCompteurResponse>> findAll(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : 
            Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Compteur> compteursPage = compteurService.findAllPaginated(pageable);
        
        List<CreateCompteurResponse> list = compteursPage.getContent().stream()
                .map(CompteurMapper::toResponse)
                .collect(Collectors.toList());
        
        return ApiResponse.<List<CreateCompteurResponse>>builder()
                .message("Liste des compteurs récupérée avec succès")
                .date(LocalDateTime.now())
                .status(200)
                .data(list)
                .totalElements(compteursPage.getTotalElements())
                .totalPages(compteursPage.getTotalPages())
                .currentPage(compteursPage.getNumber())
                .pageSize(compteursPage.getSize())
                .isFirst(compteursPage.isFirst())
                .isLast(compteursPage.isLast())
                .build();
    }

    public ApiResponse<CreateCompteurResponse> update(long id, CreateCompteurRequest request) {
        Compteur existing = compteurService.findById(id);
        if (existing == null) {
            throw new NotFoundException("Compteur avec id " + id + " introuvable");
        }
        Compteur updated = CompteurMapper.toEntity(request);
        updated.setId(id);
        updated = compteurService.update(id, updated);
        CreateCompteurResponse response = CompteurMapper.toResponse(updated);
        return ApiResponse.<CreateCompteurResponse>builder()
                .message("Compteur mis à jour avec succès")
                .date(LocalDateTime.now())
                .status(200)
                .data(response)
                .build();
    }

    public ApiResponse<String> delete(long id) {
        Compteur compteur = compteurService.findById(id);
        if (compteur == null) {
            throw new NotFoundException("Compteur avec id " + id + " introuvable");
        }
        compteurService.delete(compteur);
        return ApiResponse.<String>builder()
                .message("Compteur supprimé avec succès")
                .date(LocalDateTime.now())
                .status(200)
                .data("Compteur supprimé")
                .build();
    }

    public ApiResponse<CreateCompteurResponse> rechargeCompteur(String codeRecharge, String referenceCompteur) {
        Compteur compteur = compteurService.rechargeCompteur(codeRecharge, referenceCompteur);
        CreateCompteurResponse response = CompteurMapper.toResponse(compteur);
        return ApiResponse.<CreateCompteurResponse>builder()
                .message("Recharge effectuée avec succès")
                .date(LocalDateTime.now())
                .status(200)
                .data(response)
                .build();
    }
} 