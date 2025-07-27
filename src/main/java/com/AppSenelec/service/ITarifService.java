package com.AppSenelec.service;

import com.AppSenelec.model.Tarif;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITarifService {
    List<Tarif> findAll();
    Tarif save(Tarif tarif);
    Tarif findById(long id);
    List<Tarif> findAllNotDeleted();
    void delete(Tarif tarif);
    Tarif update(long id, Tarif tarif);
    Page<Tarif> findAllPaginated(Pageable pageable);
} 