package com.tradefinance.trade_finance_backend.repository;

import com.tradefinance.trade_finance_backend.entity.Analyse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface AnalyseRepository extends JpaRepository<Analyse, Long> {

    // Historique des analyses d'un document précis (Analyse.document -> Document.id)
    List<Analyse> findByDocumentId(Long documentId);

    // Dernière analyse en date d'un document (utile si plusieurs tentatives OCR/IA)
    Optional<Analyse> findTopByDocumentIdOrderByDateAnalyseDesc(Long documentId);

    // Toutes les analyses des documents rattachés à un dossier
    // (pas de champ "dossier" direct sur Analyse -> on traverse Document.dossier)
    @Query("SELECT a FROM Analyse a WHERE a.document.dossier.id = :dossierId")
    List<Analyse> findByDossierId(@Param("dossierId") Long dossierId);

    List<Analyse> findByStatut(String statut);
}