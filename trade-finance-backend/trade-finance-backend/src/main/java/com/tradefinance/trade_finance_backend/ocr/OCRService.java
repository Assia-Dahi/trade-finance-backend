package com.tradefinance.trade_finance_backend.ocr;

import com.tradefinance.trade_finance_backend.entity.Document;

public interface OCRService {

    /**
     * Extrait le texte brut d'un document (PDF ou image) via OCR.
     * @param document le document à traiter (utilise document.getCheminFichier())
     * @return le texte brut extrait
     */
    String extraireTexte(Document document);
}