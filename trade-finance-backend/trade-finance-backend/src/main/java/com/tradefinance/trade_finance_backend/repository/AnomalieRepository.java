package com.tradefinance.trade_finance_backend.repository;

import com.tradefinance.trade_finance_backend.entity.Anomalie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnomalieRepository extends JpaRepository<Anomalie, Long> {

    // Toutes les anomalies détectées pour un résultat d'analyse donné
    List<Anomalie> findByResultatAnalyseId(Long resultatAnalyseId);

    // Filtrer par type d'anomalie (ex: "MONTANT", "QUANTITE", "DEVISE", "DATE")
    List<Anomalie> findByType(String type);

    // Filtrer par gravité (ex: "CRITIQUE", "MOYENNE", "FAIBLE")
    List<Anomalie> findByGravite(String gravite);

    List<Anomalie> findByResultatAnalyseIdAndGravite(Long resultatAnalyseId, String gravite);
}