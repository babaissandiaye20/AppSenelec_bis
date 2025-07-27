package com.AppSenelec.service;

import com.AppSenelec.model.Compteur;
import com.AppSenelec.repository.CompteurRepository;
import com.AppSenelec.model.Achat;
import com.AppSenelec.service.IAchatService;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class CompteurService implements ICompteurService {
    private final CompteurRepository compteurRepository;
    private final IAchatService achatService;

    public CompteurService(CompteurRepository compteurRepository, IAchatService achatService) {
        this.compteurRepository = compteurRepository;
        this.achatService = achatService;
    }

    @Override
    public List<Compteur> findAll() {
        return compteurRepository.findAll();
    }

    @Override
    public Compteur save(Compteur compteur) {
        return compteurRepository.save(compteur);
    }

    @Override
    public Compteur findById(long id) {
        return compteurRepository.findById(id).orElse(null);
    }

    @Override
    public List<Compteur> findAllNotDeleted() {
        return compteurRepository.findAllNotDeleted();
    }

    @Override
    public void delete(Compteur compteur) {
        compteurRepository.delete(compteur);
    }

    @Override
    public Compteur update(long id, Compteur compteur) {
        compteur.setId(id);
        return compteurRepository.save(compteur);
    }

    public Compteur rechargeCompteur(String codeRecharge, String referenceCompteur) {
        Achat achat = achatService.findByCodeRecharge(codeRecharge);
        if (achat == null) {
            throw new IllegalArgumentException("Code de recharge invalide");
        }
        
        // Vérifier si le code a déjà été utilisé
        if (achat.isUsed()) {
            throw new IllegalArgumentException("Ce code de recharge a déjà été utilisé");
        }
        
        Compteur compteur = compteurRepository.findByReferenceNumber(referenceCompteur);
        if (compteur == null) {
            throw new IllegalArgumentException("Compteur introuvable");
        }
        
        // Marquer le code comme utilisé
        achat.setUsed(true);
        achatService.save(achat);
        
        // Recharger le compteur
        double kwhActuel = compteur.getKwhConsumption() != null ? compteur.getKwhConsumption() : 0;
        double kwhAchat = achat.getKwhPurchased() != null ? achat.getKwhPurchased() : 0;
        compteur.setKwhConsumption(kwhActuel + kwhAchat);
        return compteurRepository.save(compteur);
    }

    @Override
    public boolean existsByReferenceNumber(String referenceNumber) {
        return compteurRepository.existsByReferenceNumber(referenceNumber);
    }

    @Override
    public Page<Compteur> findAllPaginated(Pageable pageable) {
        return compteurRepository.findAll(pageable);
    }
} 