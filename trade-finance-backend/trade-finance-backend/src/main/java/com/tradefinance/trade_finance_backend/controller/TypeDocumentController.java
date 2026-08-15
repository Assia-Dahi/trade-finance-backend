package com.tradefinance.trade_finance_backend.controller;

import com.tradefinance.trade_finance_backend.dto.TypeDocumentDTO;
import com.tradefinance.trade_finance_backend.service.TypeDocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/types-documents")
public class TypeDocumentController {

    private final TypeDocumentService typeDocumentService;

    public TypeDocumentController(TypeDocumentService typeDocumentService) {
        this.typeDocumentService = typeDocumentService;
    }

    // Utile pour peupler la table au démarrage (COMMERCIAL_INVOICE, BILL_OF_LADING...)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TypeDocumentDTO creer(@RequestBody TypeDocumentDTO dto) {
        return typeDocumentService.creer(dto);
    }

    @GetMapping("/{id}")
    public TypeDocumentDTO getById(@PathVariable Long id) {
        return typeDocumentService.getById(id);
    }

    @GetMapping("/nom/{nom}")
    public TypeDocumentDTO getByNom(@PathVariable String nom) {
        return typeDocumentService.getByNom(nom);
    }

    // Utilisé par le frontend pour peupler le <select> "type de document" à l'upload
    @GetMapping
    public List<TypeDocumentDTO> getAll() {
        return typeDocumentService.getAll();
    }
}