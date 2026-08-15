package com.tradefinance.trade_finance_backend.service.impl;

import com.tradefinance.trade_finance_backend.dto.AnomalieDTO;
import com.tradefinance.trade_finance_backend.entity.Anomalie;
import com.tradefinance.trade_finance_backend.exception.ResourceNotFoundException;
import com.tradefinance.trade_finance_backend.repository.AnomalieRepository;
import com.tradefinance.trade_finance_backend.service.AnomalieService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnomalieServiceImpl implements AnomalieService {

    private final AnomalieRepository anomalieRepository;

    public AnomalieServiceImpl(AnomalieRepository anomalieRepository) {
        this.anomalieRepository = anomalieRepository;
    }

    @Override
    public AnomalieDTO getById(Long id) {
        return toDTO(anomalieRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Anomalie", id)));
    }

    @Override
    public List<AnomalieDTO> getByResultatAnalyse(Long resultatAnalyseId) {
        return anomalieRepository.findByResultatAnalyseId(resultatAnalyseId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnomalieDTO> getByType(String type) {
        return anomalieRepository.findByType(type).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnomalieDTO> getByGravite(String gravite) {
        return anomalieRepository.findByGravite(gravite).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private AnomalieDTO toDTO(Anomalie anomalie) {
        return new AnomalieDTO(
                anomalie.getId(), anomalie.getType(), anomalie.getDescription(),
                anomalie.getGravite(), anomalie.getChampConcerne()
        );
    }
}