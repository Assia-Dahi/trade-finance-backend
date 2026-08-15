package com.tradefinance.trade_finance_backend.service.impl;

import com.tradefinance.trade_finance_backend.ai.AIAnalysisResult;
import com.tradefinance.trade_finance_backend.ai.AIAnalysisService;
import com.tradefinance.trade_finance_backend.dto.AnalyseDTO;
import com.tradefinance.trade_finance_backend.dto.AnomalieDTO;
import com.tradefinance.trade_finance_backend.dto.DocumentDTO;
import com.tradefinance.trade_finance_backend.dto.ResultatAnalyseDTO;
import com.tradefinance.trade_finance_backend.entity.*;
import com.tradefinance.trade_finance_backend.exception.ResourceNotFoundException;
import com.tradefinance.trade_finance_backend.ocr.OCRService;
import com.tradefinance.trade_finance_backend.repository.AnalyseRepository;
import com.tradefinance.trade_finance_backend.repository.DocumentRepository;
import com.tradefinance.trade_finance_backend.repository.DossierRepository;
import com.tradefinance.trade_finance_backend.repository.TypeDocumentRepository;
import com.tradefinance.trade_finance_backend.service.DocumentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final DossierRepository dossierRepository;
    private final TypeDocumentRepository typeDocumentRepository;
    private final AnalyseRepository analyseRepository;
    private final OCRService ocrService;
    private final AIAnalysisService aiAnalysisService;

    @Value("${app.upload-dir:./uploads}")
    private String uploadDir;

    public DocumentServiceImpl(DocumentRepository documentRepository,
                                DossierRepository dossierRepository,
                                TypeDocumentRepository typeDocumentRepository,
                                AnalyseRepository analyseRepository,
                                OCRService ocrService,
                                AIAnalysisService aiAnalysisService) {
        this.documentRepository = documentRepository;
        this.dossierRepository = dossierRepository;
        this.typeDocumentRepository = typeDocumentRepository;
        this.analyseRepository = analyseRepository;
        this.ocrService = ocrService;
        this.aiAnalysisService = aiAnalysisService;
    }

    @Override
    public DocumentDTO uploader(Long dossierId, Long typeDocumentId, MultipartFile fichier) {
        Dossier dossier = dossierRepository.findById(dossierId)
                .orElseThrow(() -> ResourceNotFoundException.of("Dossier", dossierId));

        TypeDocument type = typeDocumentRepository.findById(typeDocumentId)
                .orElseThrow(() -> ResourceNotFoundException.of("TypeDocument", typeDocumentId));

        String cheminFichier = sauvegarderSurDisque(fichier);

        Document document = new Document();
        document.setNomFichier(fichier.getOriginalFilename());
        document.setCheminFichier(cheminFichier);
        document.setDateUpload(LocalDateTime.now());
        document.setStatut("EN_ATTENTE_ANALYSE");
        document.setDossier(dossier);
        document.setTypeDocument(type);

        // 1. Sauvegarde initiale du document
        Document savedDocument = documentRepository.save(document);

        // 2. Lancer OCR + analyse IA, construire Analyse + ResultatAnalyse + Anomalies
        lancerAnalyse(savedDocument, type);

        // 3. Recharger le document (statut mis à jour) pour le retourner
        Document finalDoc = documentRepository.findById(savedDocument.getId())
                .orElseThrow(() -> ResourceNotFoundException.of("Document", savedDocument.getId()));

        return toDTO(finalDoc);
    }

    private void lancerAnalyse(Document document, TypeDocument type) {
        Analyse analyse = new Analyse();
        analyse.setDateAnalyse(LocalDateTime.now());
        analyse.setTypeAnalyse("OCR_IA");
        analyse.setDocument(document);

        try {
            // --- OCR ---
            String texteOcr = ocrService.extraireTexte(document);
            document.setTexteOcr(texteOcr);

            // --- Analyse IA ---
            AIAnalysisResult aiResultat = aiAnalysisService.analyser(texteOcr, type.getNom());

            // --- Construire ResultatAnalyse ---
            ResultatAnalyse resultatAnalyse = new ResultatAnalyse();
            resultatAnalyse.setConforme(aiResultat.isConforme());
            resultatAnalyse.setScoreConformite(aiResultat.getScoreConformite());
            resultatAnalyse.setResume(aiResultat.getResume());
            resultatAnalyse.setAnalyse(analyse);

            // --- Construire les Anomalies ---
            List<Anomalie> anomalies = new ArrayList<>();
            for (AIAnalysisResult.AnomalieDetectee a : aiResultat.getAnomalies()) {
                Anomalie anomalie = new Anomalie();
                anomalie.setType(a.getType());
                anomalie.setDescription(a.getDescription());
                anomalie.setGravite(a.getGravite());
                anomalie.setChampConcerne(a.getChampConcerne());
                anomalie.setResultatAnalyse(resultatAnalyse);
                anomalies.add(anomalie);
            }
            resultatAnalyse.setAnomalies(anomalies);

            analyse.setResultat(resultatAnalyse);
            analyse.setStatut("TERMINEE");
            document.setStatut("ANALYSE_TERMINEE");

        } catch (Exception e) {
            analyse.setStatut("ERREUR");
            document.setStatut("ERREUR_ANALYSE");
        }

        // cascade = ALL sur Analyse -> ResultatAnalyse -> Anomalie donc un seul save suffit
        analyseRepository.save(analyse);
        documentRepository.save(document);
    }

    @Override
    public DocumentDTO getById(Long id) {
        return toDTO(documentRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Document", id)));
    }

    @Override
    public List<DocumentDTO> getByDossier(Long dossierId) {
        return documentRepository.findByDossierId(dossierId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DocumentDTO> getByDossierEtType(Long dossierId, Long typeDocumentId) {
        return documentRepository.findByDossierIdAndTypeDocumentId(dossierId, typeDocumentId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (!documentRepository.existsById(id)) {
            throw ResourceNotFoundException.of("Document", id);
        }
        documentRepository.deleteById(id);
    }

    @Override
    public AnalyseDTO getResultatAnalyse(Long documentId) {
        Analyse analyse = analyseRepository.findTopByDocumentIdOrderByDateAnalyseDesc(documentId)
                .orElseThrow(() -> ResourceNotFoundException.of("Analyse", documentId));

        ResultatAnalyse resultat = analyse.getResultat();

        ResultatAnalyseDTO resultatDTO = null;
        if (resultat != null) {
            List<AnomalieDTO> anomaliesDTO = resultat.getAnomalies().stream()
                    .map(a -> new AnomalieDTO(a.getId(), a.getType(), a.getDescription(), a.getGravite(), a.getChampConcerne()))
                    .collect(Collectors.toList());

            resultatDTO = new ResultatAnalyseDTO(
                    resultat.getId(),
                    resultat.isConforme(),
                    resultat.getScoreConformite(),
                    resultat.getResume(),
                    anomaliesDTO
            );
        }

        Document document = analyse.getDocument();

        return new AnalyseDTO(
                analyse.getId(),
                analyse.getDateAnalyse(),
                analyse.getStatut(),
                analyse.getTypeAnalyse(),
                document != null ? document.getId() : null,
                document != null ? document.getNomFichier() : null,
                resultatDTO
        );
    }

    private String sauvegarderSurDisque(MultipartFile fichier) {
        try {
            Path dossierUpload = Paths.get(uploadDir);
            if (!Files.exists(dossierUpload)) {
                Files.createDirectories(dossierUpload);
            }
            String nomUnique = UUID.randomUUID() + "_" + fichier.getOriginalFilename();
            Path cheminComplet = dossierUpload.resolve(nomUnique);
            Files.copy(fichier.getInputStream(), cheminComplet);
            return cheminComplet.toString();
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du fichier : " + fichier.getOriginalFilename(), e);
        }
    }

    private DocumentDTO toDTO(Document document) {
        return new DocumentDTO(
                document.getId(), document.getNomFichier(), document.getCheminFichier(),
                document.getDateUpload(), document.getStatut(),
                document.getTypeDocument() != null ? document.getTypeDocument().getId() : null,
                document.getTypeDocument() != null ? document.getTypeDocument().getNom() : null,
                document.getDossier() != null ? document.getDossier().getId() : null
        );
    }
}