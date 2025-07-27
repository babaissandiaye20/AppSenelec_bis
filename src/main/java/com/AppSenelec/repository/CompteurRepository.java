package com.AppSenelec.repository;

import com.AppSenelec.model.Compteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompteurRepository extends JpaRepository<Compteur, Long> {
    @Query("SELECT c FROM Compteur c WHERE c.isDeleted = false")
    List<Compteur> findAllNotDeleted();
    Compteur findByReferenceNumber(String referenceNumber);
    boolean existsByReferenceNumber(String referenceNumber);
} 