package com.tradefinance.trade_finance_backend.service;

import com.tradefinance.trade_finance_backend.dto.AnalyseDTO;
import java.util.List;

public interface AnalyseService {

    AnalyseDTO lancerAnalyse(Long documentId);

    AnalyseDTO getById(Long id);

    List<AnalyseDTO> getByDocument(Long documentId);

    List<AnalyseDTO> getByDossier(Long dossierId);
}