package com.tradefinance.trade_finance_backend.repository;

import com.tradefinance.trade_finance_backend.entity.ResultatAnalyse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ResultatAnalyseRepository extends JpaRepository<ResultatAnalyse, Long> {

    // Un résultat appartient à une seule analyse (relation OneToOne)
    Optional<ResultatAnalyse> findByAnalyseId(Long analyseId);

    // Utile pour un dashboard : tous les résultats non conformes
    List<ResultatAnalyse> findByConformeFalse();

    List<ResultatAnalyse> findByConformeTrue();

    // Résultats en dessous d'un certain score de confiance (à vérifier en priorité par l'agent)
    List<ResultatAnalyse> findByScoreConformiteLessThan(Double seuil);

    // Tous les résultats liés à un dossier (traverse Analyse -> Document -> Dossier)
    @Query("SELECT r FROM ResultatAnalyse r WHERE r.analyse.document.dossier.id = :dossierId")
    List<ResultatAnalyse> findByDossierId(@Param("dossierId") Long dossierId);
}