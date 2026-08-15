package com.tradefinance.trade_finance_backend.controller;

import com.tradefinance.trade_finance_backend.dto.AnalyseDTO;
import com.tradefinance.trade_finance_backend.service.AnalyseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analyses")
public class AnalyseController {

    private final AnalyseService analyseService;

    public AnalyseController(AnalyseService analyseService) {
        this.analyseService = analyseService;
    }

    // C'EST l'endpoint appelé quand l'agent clique sur "Lancer OCR" dans l'interface.
    // Il déclenche OCR + IA et retourne directement le résultat structuré
    // (score de conformité + anomalies) pour remplir le formulaire.
    @PostMapping("/lancer/{documentId}")
    public AnalyseDTO lancerAnalyse(@PathVariable Long documentId) {
        return analyseService.lancerAnalyse(documentId);
    }

    @GetMapping("/{id}")
    public AnalyseDTO getById(@PathVariable Long id) {
        return analyseService.getById(id);
    }

    @GetMapping("/document/{documentId}")
    public List<AnalyseDTO> getByDocument(@PathVariable Long documentId) {
        return analyseService.getByDocument(documentId);
    }

    // Vue d'ensemble de toutes les analyses d'un dossier (tous documents confondus)
    @GetMapping("/dossier/{dossierId}")
    public List<AnalyseDTO> getByDossier(@PathVariable Long dossierId) {
        return analyseService.getByDossier(dossierId);
    }
}