package com.tradefinance.trade_finance_backend.service;

import com.tradefinance.trade_finance_backend.dto.ResultatAnalyseDTO;
import java.util.List;

public interface ResultatAnalyseService {

    ResultatAnalyseDTO getById(Long id);

    ResultatAnalyseDTO getByAnalyse(Long analyseId);

    List<ResultatAnalyseDTO> getNonConformes();

    List<ResultatAnalyseDTO> getByDossier(Long dossierId);
}