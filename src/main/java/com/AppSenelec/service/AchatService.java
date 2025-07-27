package com.AppSenelec.service;

import com.AppSenelec.model.Achat;
import com.AppSenelec.repository.AchatRepository;
import com.AppSenelec.util.CodeGeneratorUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AchatService implements IAchatService {
    private final AchatRepository achatRepository;
    private final CodeGeneratorUtil codeGeneratorUtil;

    public AchatService(AchatRepository achatRepository, CodeGeneratorUtil codeGeneratorUtil) {
        this.achatRepository = achatRepository;
        this.codeGeneratorUtil = codeGeneratorUtil;
    }

    @Override
    public List<Achat> findAll() {
        return achatRepository.findAll();
    }

    @Override
    public Achat save(Achat achat) {
        return achatRepository.save(achat);
    }

    @Override
    public Achat findById(long id) {
        return achatRepository.findById(id).orElse(null);
    }

    @Override
    public List<Achat> findAllNotDeleted() {
        return achatRepository.findAllNotDeleted();
    }

    @Override
    public void delete(Achat achat) {
        achatRepository.delete(achat);
    }

    @Override
    public Achat update(long id, Achat achat) {
        achat.setId(id);
        return achatRepository.save(achat);
    }

    @Override
    public boolean existsByCodeRecharge(String codeRecharge) {
        return achatRepository.existsByCodeRecharge(codeRecharge);
    }

    @Override
    public Achat findByCodeRecharge(String codeRecharge) {
        return achatRepository.findByCodeRecharge(codeRecharge);
    }

    /**
     * Génère un code de recharge unique
     * @return Code de recharge unique
     */
    public String generateUniqueCodeRecharge() {
        return codeGeneratorUtil.generateUniqueCode(this::existsByCodeRecharge);
    }

    @Override
    public Page<Achat> findAllPaginated(Pageable pageable) {
        return achatRepository.findAll(pageable);
    }
} 