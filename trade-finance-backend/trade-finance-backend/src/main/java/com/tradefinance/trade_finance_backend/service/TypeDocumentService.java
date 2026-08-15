package com.tradefinance.trade_finance_backend.service;

import com.tradefinance.trade_finance_backend.dto.TypeDocumentDTO;
import java.util.List;

public interface TypeDocumentService {

    TypeDocumentDTO creer(TypeDocumentDTO dto);

    TypeDocumentDTO getById(Long id);

    TypeDocumentDTO getByNom(String nom);

    List<TypeDocumentDTO> getAll();
}