package com.tradefinance.trade_finance_backend.controller;

import com.tradefinance.trade_finance_backend.dto.DossierDTO;
import com.tradefinance.trade_finance_backend.service.DossierService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dossiers")
public class DossierController {

    private final DossierService dossierService;

    public DossierController(DossierService dossierService) {
        this.dossierService = dossierService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DossierDTO creer(@RequestParam String reference,
                             @RequestParam String typeOperation,
                             @RequestParam Long agentId) {
        return dossierService.creer(reference, typeOperation, agentId);
    }

    // Retourne le dossier AVEC ses documents (écran de détail)
    @GetMapping("/{id}")
    public DossierDTO getById(@PathVariable Long id) {
        return dossierService.getById(id);
    }

    @GetMapping
    public List<DossierDTO> getAll() {
        return dossierService.getAll();
    }

    @GetMapping("/agent/{agentId}")
    public List<DossierDTO> getByAgent(@PathVariable Long agentId) {
        return dossierService.getByAgent(agentId);
    }

    // Ex: GET /api/dossiers/statut/EN_COURS
    @GetMapping("/statut/{statut}")
    public List<DossierDTO> getByStatut(@PathVariable String statut) {
        return dossierService.getByStatut(statut);
    }

    @PatchMapping("/{id}/statut")
    public DossierDTO changerStatut(@PathVariable Long id, @RequestParam String nouveauStatut) {
        return dossierService.changerStatut(id, nouveauStatut);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        dossierService.delete(id);
    }
}