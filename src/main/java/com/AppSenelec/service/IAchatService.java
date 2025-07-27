package com.AppSenelec.service;

import com.AppSenelec.model.Achat;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAchatService {
    List<Achat> findAll();
    Achat save(Achat achat);
    Achat findById(long id);
    List<Achat> findAllNotDeleted();
    void delete(Achat achat);
    Achat update(long id, Achat achat);
    boolean existsByCodeRecharge(String codeRecharge);
    Achat findByCodeRecharge(String codeRecharge);
    String generateUniqueCodeRecharge();
    Page<Achat> findAllPaginated(Pageable pageable);
} 