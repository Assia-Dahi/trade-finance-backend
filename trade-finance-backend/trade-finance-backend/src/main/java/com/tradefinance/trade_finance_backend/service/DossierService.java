package com.tradefinance.trade_finance_backend.service;

import com.tradefinance.trade_finance_backend.dto.DossierDTO;
import java.util.List;

public interface DossierService {

    DossierDTO creer(String reference, String typeOperation, Long agentId);

    DossierDTO getById(Long id);

    List<DossierDTO> getAll();

    List<DossierDTO> getByAgent(Long agentId);

    List<DossierDTO> getByStatut(String statut);

    DossierDTO changerStatut(Long id, String nouveauStatut);

    void delete(Long id);
}