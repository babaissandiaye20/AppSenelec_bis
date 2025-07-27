package com.AppSenelec.repository;

import com.AppSenelec.model.Achat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AchatRepository extends JpaRepository<Achat, Long> {
    @Query("SELECT a FROM Achat a WHERE a.isDeleted = false")
    List<Achat> findAllNotDeleted();
    boolean existsByCodeRecharge(String codeRecharge);
    Achat findByCodeRecharge(String codeRecharge);
} 