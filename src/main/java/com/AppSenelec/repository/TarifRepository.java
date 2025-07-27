package com.AppSenelec.repository;

import com.AppSenelec.model.Tarif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TarifRepository extends JpaRepository<Tarif, Long> {
    @Query("SELECT t FROM Tarif t WHERE t.isDeleted = false")
    List<Tarif> findAllNotDeleted();
} 