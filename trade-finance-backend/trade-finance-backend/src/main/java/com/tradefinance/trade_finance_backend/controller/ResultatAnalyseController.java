package com.tradefinance.trade_finance_backend.controller;

import com.tradefinance.trade_finance_backend.dto.ResultatAnalyseDTO;
import com.tradefinance.trade_finance_backend.service.ResultatAnalyseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resultats")
public class ResultatAnalyseController {

    private final ResultatAnalyseService resultatAnalyseService;

    public ResultatAnalyseController(ResultatAnalyseService resultatAnalyseService) {
        this.resultatAnalyseService = resultatAnalyseService;
    }

    @GetMapping("/{id}")
    public ResultatAnalyseDTO getById(@PathVariable Long id) {
        return resultatAnalyseService.getById(id);
    }

    @GetMapping("/analyse/{analyseId}")
    public ResultatAnalyseDTO getByAnalyse(@PathVariable Long analyseId) {
        return resultatAnalyseService.getByAnalyse(analyseId);
    }

    // Utile pour un dashboard agent : "dossiers à vérifier en priorité"
    @GetMapping("/non-conformes")
    public List<ResultatAnalyseDTO> getNonConformes() {
        return resultatAnalyseService.getNonConformes();
    }

    @GetMapping("/dossier/{dossierId}")
    public List<ResultatAnalyseDTO> getByDossier(@PathVariable Long dossierId) {
        return resultatAnalyseService.getByDossier(dossierId);
    }
}