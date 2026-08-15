package com.tradefinance.trade_finance_backend.service.impl;

import com.tradefinance.trade_finance_backend.dto.TypeDocumentDTO;
import com.tradefinance.trade_finance_backend.entity.TypeDocument;
import com.tradefinance.trade_finance_backend.exception.ResourceNotFoundException;
import com.tradefinance.trade_finance_backend.repository.TypeDocumentRepository;
import com.tradefinance.trade_finance_backend.service.TypeDocumentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeDocumentServiceImpl implements TypeDocumentService {

    private final TypeDocumentRepository typeDocumentRepository;

    public TypeDocumentServiceImpl(TypeDocumentRepository typeDocumentRepository) {
        this.typeDocumentRepository = typeDocumentRepository;
    }

    @Override
    public TypeDocumentDTO creer(TypeDocumentDTO dto) {
        TypeDocument type = new TypeDocument();
        type.setNom(dto.getNom());
        type.setDescription(dto.getDescription());
        return toDTO(typeDocumentRepository.save(type));
    }

    @Override
    public TypeDocumentDTO getById(Long id) {
        return toDTO(typeDocumentRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("TypeDocument", id)));
    }

    @Override
    public TypeDocumentDTO getByNom(String nom) {
        return typeDocumentRepository.findByNom(nom)
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("TypeDocument introuvable avec le nom : " + nom));
    }

    @Override
    public List<TypeDocumentDTO> getAll() {
        return typeDocumentRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private TypeDocumentDTO toDTO(TypeDocument type) {
        return new TypeDocumentDTO(type.getId(), type.getNom(), type.getDescription());
    }
}