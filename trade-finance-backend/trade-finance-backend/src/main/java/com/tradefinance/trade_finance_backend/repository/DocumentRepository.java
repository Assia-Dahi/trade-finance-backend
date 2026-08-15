package com.tradefinance.trade_finance_backend.repository;

import com.tradefinance.trade_finance_backend.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    // Tous les documents d'un dossier (pour affichage + lancement OCR global)
    List<Document> findByDossierId(Long dossierId);

    // Documents d'un dossier filtrés par type (ex: uniquement les Bill of Lading)
    List<Document> findByDossierIdAndTypeDocumentId(Long dossierId, Long typeDocumentId);

    // Utile si tu veux chercher par nom de type directement (ex: "BILL_OF_LADING")
    List<Document> findByDossierIdAndTypeDocumentNom(Long dossierId, String typeDocumentNom);

    // Documents pas encore passés à l'OCR (utile pour le batch de traitement)
    List<Document> findByStatut(String statut);
}