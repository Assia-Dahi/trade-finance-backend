package com.tradefinance.trade_finance_backend.controller;

import com.tradefinance.trade_finance_backend.dto.AnalyseDTO;
import com.tradefinance.trade_finance_backend.dto.DocumentDTO;
import com.tradefinance.trade_finance_backend.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    // C'est l'endpoint appelé par le bouton "Attacher les documents PDF" côté frontend.
    // multipart/form-data : dossierId, typeDocumentId, fichier
    // Déclenche automatiquement OCR + analyse IA
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public DocumentDTO uploader(@RequestParam Long dossierId,
                                 @RequestParam Long typeDocumentId,
                                 @RequestParam("fichier") MultipartFile fichier) {
        return documentService.uploader(dossierId, typeDocumentId, fichier);
    }

    @GetMapping("/{id}")
    public DocumentDTO getById(@PathVariable Long id) {
        return documentService.getById(id);
    }

    @GetMapping("/dossier/{dossierId}")
    public List<DocumentDTO> getByDossier(@PathVariable Long dossierId) {
        return documentService.getByDossier(dossierId);
    }

    @GetMapping("/dossier/{dossierId}/type/{typeDocumentId}")
    public List<DocumentDTO> getByDossierEtType(@PathVariable Long dossierId,
                                                 @PathVariable Long typeDocumentId) {
        return documentService.getByDossierEtType(dossierId, typeDocumentId);
    }

    // Récupère le résultat de l'analyse OCR + IA pour un document donné
    @GetMapping("/{id}/resultat")
    public AnalyseDTO getResultatAnalyse(@PathVariable Long id) {
        return documentService.getResultatAnalyse(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        documentService.delete(id);
    }
}