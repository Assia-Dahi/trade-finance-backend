package com.tradefinance.trade_finance_backend.service;

import com.tradefinance.trade_finance_backend.dto.AnalyseDTO;
import com.tradefinance.trade_finance_backend.dto.DocumentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {

    DocumentDTO uploader(Long dossierId, Long typeDocumentId, MultipartFile fichier);

    DocumentDTO getById(Long id);

    List<DocumentDTO> getByDossier(Long dossierId);

    List<DocumentDTO> getByDossierEtType(Long dossierId, Long typeDocumentId);

    void delete(Long id);

    AnalyseDTO getResultatAnalyse(Long documentId);
}