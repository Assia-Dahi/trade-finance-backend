package com.tradefinance.trade_finance_backend.controller;

import com.tradefinance.trade_finance_backend.dto.AnomalieDTO;
import com.tradefinance.trade_finance_backend.service.AnomalieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anomalies")
public class AnomalieController {

    private final AnomalieService anomalieService;

    public AnomalieController(AnomalieService anomalieService) {
        this.anomalieService = anomalieService;
    }

    @GetMapping("/{id}")
    public AnomalieDTO getById(@PathVariable Long id) {
        return anomalieService.getById(id);
    }

    @GetMapping("/resultat/{resultatAnalyseId}")
    public List<AnomalieDTO> getByResultatAnalyse(@PathVariable Long resultatAnalyseId) {
        return anomalieService.getByResultatAnalyse(resultatAnalyseId);
    }

    // Ex: GET /api/anomalies/type/MONTANT
    @GetMapping("/type/{type}")
    public List<AnomalieDTO> getByType(@PathVariable String type) {
        return anomalieService.getByType(type);
    }

    // Ex: GET /api/anomalies/gravite/CRITIQUE
    @GetMapping("/gravite/{gravite}")
    public List<AnomalieDTO> getByGravite(@PathVariable String gravite) {
        return anomalieService.getByGravite(gravite);
    }
}