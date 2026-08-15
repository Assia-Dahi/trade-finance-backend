package com.tradefinance.trade_finance_backend.ai;

public interface AIAnalysisService {

    /**
     * Analyse le texte OCR d'un document : extraction d'informations structurées
     * et détection d'anomalies.
     * @param texteOcr le texte brut extrait par l'OCR
     * @param typeDocumentNom le type du document (ex: "COMMERCIAL_INVOICE") pour adapter l'analyse
     * @return un résultat structuré (conformité, score, anomalies)
     */
    AIAnalysisResult analyser(String texteOcr, String typeDocumentNom);
}