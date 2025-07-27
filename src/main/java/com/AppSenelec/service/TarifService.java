package com.AppSenelec.service;

import com.AppSenelec.model.Tarif;
import com.AppSenelec.repository.TarifRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class TarifService implements ITarifService {
    private final TarifRepository tarifRepository;

    public TarifService(TarifRepository tarifRepository) {
        this.tarifRepository = tarifRepository;
    }

    @Override
    public List<Tarif> findAll() {
        return tarifRepository.findAll();
    }

    @Override
    public Tarif save(Tarif tarif) {
        return tarifRepository.save(tarif);
    }

    @Override
    public Tarif findById(long id) {
        return tarifRepository.findById(id).orElse(null);
    }

    @Override
    public List<Tarif> findAllNotDeleted() {
        return tarifRepository.findAllNotDeleted();
    }

    @Override
    public void delete(Tarif tarif) {
        tarifRepository.delete(tarif);
    }

    @Override
    public Tarif update(long id, Tarif tarif) {
        tarif.setId(id);
        return tarifRepository.save(tarif);
    }

    @Override
    public Page<Tarif> findAllPaginated(Pageable pageable) {
        return tarifRepository.findAll(pageable);
    }
} 