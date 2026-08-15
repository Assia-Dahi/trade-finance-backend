package com.tradefinance.trade_finance_backend.service.impl;

import com.tradefinance.trade_finance_backend.dto.AnomalieDTO;
import com.tradefinance.trade_finance_backend.dto.ResultatAnalyseDTO;
import com.tradefinance.trade_finance_backend.entity.Anomalie;
import com.tradefinance.trade_finance_backend.entity.ResultatAnalyse;
import com.tradefinance.trade_finance_backend.exception.ResourceNotFoundException;
import com.tradefinance.trade_finance_backend.repository.ResultatAnalyseRepository;
import com.tradefinance.trade_finance_backend.service.ResultatAnalyseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultatAnalyseServiceImpl implements ResultatAnalyseService {

    private final ResultatAnalyseRepository resultatAnalyseRepository;

    public ResultatAnalyseServiceImpl(ResultatAnalyseRepository resultatAnalyseRepository) {
        this.resultatAnalyseRepository = resultatAnalyseRepository;
    }

    @Override
    public ResultatAnalyseDTO getById(Long id) {
        return toDTO(resultatAnalyseRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("ResultatAnalyse", id)));
    }

    @Override
    public ResultatAnalyseDTO getByAnalyse(Long analyseId) {
        return resultatAnalyseRepository.findByAnalyseId(analyseId)
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Aucun résultat trouvé pour l'analyse id : " + analyseId));
    }

    @Override
    public List<ResultatAnalyseDTO> getNonConformes() {
        return resultatAnalyseRepository.findByConformeFalse().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ResultatAnalyseDTO> getByDossier(Long dossierId) {
        return resultatAnalyseRepository.findByDossierId(dossierId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ResultatAnalyseDTO toDTO(ResultatAnalyse resultat) {
        List<AnomalieDTO> anomaliesDTO = resultat.getAnomalies() != null
                ? resultat.getAnomalies().stream().map(this::anomalieToDTO).collect(Collectors.toList())
                : List.of();

        return new ResultatAnalyseDTO(
                resultat.getId(), resultat.isConforme(), resultat.getScoreConformite(),
                resultat.getResume(), anomaliesDTO
        );
    }

    private AnomalieDTO anomalieToDTO(Anomalie anomalie) {
        return new AnomalieDTO(
                anomalie.getId(), anomalie.getType(), anomalie.getDescription(),
                anomalie.getGravite(), anomalie.getChampConcerne()
        );
    }
}