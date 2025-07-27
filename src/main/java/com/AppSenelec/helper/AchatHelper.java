package com.AppSenelec.helper;

import com.AppSenelec.dto.CreateAchatRequest;
import com.AppSenelec.dto.CreateAchatResponse;
import com.AppSenelec.exception.NotFoundException;
import com.AppSenelec.mapper.AchatMapper;
import com.AppSenelec.model.Achat;
import com.AppSenelec.model.Tarif;
import com.AppSenelec.model.TransactionStatus;
import com.AppSenelec.response.ApiResponse;
import com.AppSenelec.service.IAchatService;
import com.AppSenelec.service.ITarifService;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Component
public class AchatHelper {
    private final IAchatService achatService;
    private final ITarifService tarifService;

    public AchatHelper(IAchatService achatService, ITarifService tarifService) {
        this.achatService = achatService;
        this.tarifService = tarifService;
    }

    public ApiResponse<CreateAchatResponse> save(CreateAchatRequest request) {
        // Récupérer le tarif sélectionné
        Tarif tarif = tarifService.findById(request.getTarifId());
        if (tarif == null) {
            throw new IllegalArgumentException("Tarif avec id " + request.getTarifId() + " introuvable");
        }
        
        // Vérifier que le montant est supérieur ou égal au montant du tarif
        double montant = request.getAmount();
        double montantTarif = tarif.getAmount();
        
        if (montant < montantTarif) {
            throw new IllegalArgumentException("Le montant " + montant + " est inférieur au montant minimum requis " + montantTarif + " pour ce tarif");
        }
        
        // Calculer les kWh avec la formule : (montant achat × kWh tarif) ÷ montant tarif
        double kwh = (montant * tarif.getKwh()) / montantTarif;
        BigDecimal bd = new BigDecimal(kwh).setScale(1, RoundingMode.UP);
        double kwhArrondi = bd.doubleValue();
        
        // Générer un code de recharge unique de 20 chiffres
        String codeRecharge = achatService.generateUniqueCodeRecharge();
        
        // Préparer l'achat
        Achat achat = AchatMapper.toEntity(request);
        achat.setKwhPurchased(kwhArrondi);
        achat.setCodeRecharge(codeRecharge);
        achat.setTransactionStatus(TransactionStatus.COMPLETED);
        achat.setUsed(false);
        achat = achatService.save(achat);
        CreateAchatResponse response = AchatMapper.toResponse(achat);
        return ApiResponse.<CreateAchatResponse>builder()
                .message("Achat créé avec succès")
                .date(LocalDateTime.now())
                .status(201)
                .data(response)
                .build();
    }

    public ApiResponse<CreateAchatResponse> findById(long id) {
        Achat achat = achatService.findById(id);
        if (achat == null) {
            throw new NotFoundException("Achat avec id " + id + " introuvable");
        }
        CreateAchatResponse response = AchatMapper.toResponse(achat);
        return ApiResponse.<CreateAchatResponse>builder()
                .message("Achat trouvé")
                .date(LocalDateTime.now())
                .status(200)
                .data(response)
                .build();
    }

    public ApiResponse<List<CreateAchatResponse>> findAll(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : 
            Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Achat> achatsPage = achatService.findAllPaginated(pageable);
        
        List<CreateAchatResponse> list = achatsPage.getContent().stream()
                .map(AchatMapper::toResponse)
                .collect(Collectors.toList());
        
        return ApiResponse.<List<CreateAchatResponse>>builder()
                .message("Liste des achats récupérée avec succès")
                .date(LocalDateTime.now())
                .status(200)
                .data(list)
                .totalElements(achatsPage.getTotalElements())
                .totalPages(achatsPage.getTotalPages())
                .currentPage(achatsPage.getNumber())
                .pageSize(achatsPage.getSize())
                .isFirst(achatsPage.isFirst())
                .isLast(achatsPage.isLast())
                .build();
    }

    public ApiResponse<CreateAchatResponse> update(long id, CreateAchatRequest request) {
        Achat existing = achatService.findById(id);
        if (existing == null) {
            throw new NotFoundException("Achat avec id " + id + " introuvable");
        }
        Achat updated = AchatMapper.toEntity(request);
        updated.setId(id);
        updated = achatService.update(id, updated);
        CreateAchatResponse response = AchatMapper.toResponse(updated);
        return ApiResponse.<CreateAchatResponse>builder()
                .message("Achat mis à jour avec succès")
                .date(LocalDateTime.now())
                .status(200)
                .data(response)
                .build();
    }

    public ApiResponse<String> delete(long id) {
        Achat achat = achatService.findById(id);
        if (achat == null) {
            throw new NotFoundException("Achat avec id " + id + " introuvable");
        }
        achatService.delete(achat);
        return ApiResponse.<String>builder()
                .message("Achat supprimé avec succès")
                .date(LocalDateTime.now())
                .status(200)
                .data("Achat supprimé")
                .build();
    }
} 