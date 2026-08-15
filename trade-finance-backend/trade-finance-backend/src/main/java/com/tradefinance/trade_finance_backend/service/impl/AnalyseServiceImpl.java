package com.tradefinance.trade_finance_backend.service.impl;

import com.tradefinance.trade_finance_backend.ai.AIAnalysisResult;
import com.tradefinance.trade_finance_backend.ai.AIAnalysisService;
import com.tradefinance.trade_finance_backend.dto.AnalyseDTO;
import com.tradefinance.trade_finance_backend.dto.AnomalieDTO;
import com.tradefinance.trade_finance_backend.dto.ResultatAnalyseDTO;
import com.tradefinance.trade_finance_backend.entity.*;
import com.tradefinance.trade_finance_backend.exception.ResourceNotFoundException;
import com.tradefinance.trade_finance_backend.ocr.OCRService;
import com.tradefinance.trade_finance_backend.repository.AnalyseRepository;
import com.tradefinance.trade_finance_backend.repository.DocumentRepository;
import com.tradefinance.trade_finance_backend.service.AnalyseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalyseServiceImpl implements AnalyseService {

    private final AnalyseRepository analyseRepository;
    private final DocumentRepository documentRepository;
    private final OCRService ocrService;
    private final AIAnalysisService aiAnalysisService;

    public AnalyseServiceImpl(AnalyseRepository analyseRepository,
                               DocumentRepository documentRepository,
                               OCRService ocrService,
                               AIAnalysisService aiAnalysisService) {
        this.analyseRepository = analyseRepository;
        this.documentRepository = documentRepository;
        this.ocrService = ocrService;
        this.aiAnalysisService = aiAnalysisService;
    }

    @Override
    @Transactional
    public AnalyseDTO lancerAnalyse(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> ResourceNotFoundException.of("Document", documentId));

        String texteOcr = ocrService.extraireTexte(document);
        document.setTexteOcr(texteOcr);
        document.setStatut("OCR_TERMINE");

        String typeDocumentNom = document.getTypeDocument() != null
                ? document.getTypeDocument().getNom() : null;
        AIAnalysisResult resultatIA = aiAnalysisService.analyser(texteOcr, typeDocumentNom);

        Analyse analyse = new Analyse();
        analyse.setDateAnalyse(LocalDateTime.now());
        analyse.setStatut("TERMINEE");
        analyse.setTypeAnalyse("OCR_IA");
        analyse.setDocument(document);

        ResultatAnalyse resultat = new ResultatAnalyse();
        resultat.setConforme(resultatIA.isConforme());
        resultat.setScoreConformite(resultatIA.getScoreConformite());
        resultat.setResume(resultatIA.getResume());
        resultat.setAnalyse(analyse);

        resultatIA.getAnomalies().forEach(a -> {
            Anomalie anomalie = new Anomalie();
            anomalie.setType(a.getType());
            anomalie.setDescription(a.getDescription());
            anomalie.setGravite(a.getGravite());
            anomalie.setChampConcerne(a.getChampConcerne());
            anomalie.setResultatAnalyse(resultat);
            resultat.getAnomalies().add(anomalie);
        });

        analyse.setResultat(resultat);
        document.setStatut(resultatIA.isConforme() ? "ANALYSE_CONFORME" : "ANALYSE_ANOMALIE_DETECTEE");

        Analyse saved = analyseRepository.save(analyse);
        documentRepository.save(document);

        return toDTO(saved);
    }

    @Override
    public AnalyseDTO getById(Long id) {
        return toDTO(analyseRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Analyse", id)));
    }

    @Override
    public List<AnalyseDTO> getByDocument(Long documentId) {
        return analyseRepository.findByDocumentId(documentId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnalyseDTO> getByDossier(Long dossierId) {
        return analyseRepository.findByDossierId(dossierId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private AnalyseDTO toDTO(Analyse analyse) {
        return new AnalyseDTO(
                analyse.getId(), analyse.getDateAnalyse(), analyse.getStatut(), analyse.getTypeAnalyse(),
                analyse.getDocument() != null ? analyse.getDocument().getId() : null,
                analyse.getDocument() != null ? analyse.getDocument().getNomFichier() : null,
                analyse.getResultat() != null ? resultatToDTO(analyse.getResultat()) : null
        );
    }

    private ResultatAnalyseDTO resultatToDTO(ResultatAnalyse resultat) {
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