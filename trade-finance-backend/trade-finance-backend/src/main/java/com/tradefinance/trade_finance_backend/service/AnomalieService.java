package com.tradefinance.trade_finance_backend.service;

import com.tradefinance.trade_finance_backend.dto.AnomalieDTO;
import java.util.List;

public interface AnomalieService {

    AnomalieDTO getById(Long id);

    List<AnomalieDTO> getByResultatAnalyse(Long resultatAnalyseId);

    List<AnomalieDTO> getByType(String type);

    List<AnomalieDTO> getByGravite(String gravite);
}