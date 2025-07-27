package com.AppSenelec.service;

import com.AppSenelec.model.Compteur;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICompteurService {
    List<Compteur> findAll();
    Compteur save(Compteur compteur);
    Compteur findById(long id);
    List<Compteur> findAllNotDeleted();
    void delete(Compteur compteur);
    Compteur update(long id, Compteur compteur);
    Compteur rechargeCompteur(String codeRecharge, String referenceCompteur);
    boolean existsByReferenceNumber(String referenceNumber);
    Page<Compteur> findAllPaginated(Pageable pageable);
} 