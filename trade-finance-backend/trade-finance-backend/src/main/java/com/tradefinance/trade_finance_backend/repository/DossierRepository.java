package com.tradefinance.trade_finance_backend.repository;

import com.tradefinance.trade_finance_backend.entity.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DossierRepository extends JpaRepository<Dossier, Long> {

    Optional<Dossier> findByReference(String reference);

    // Dossiers gérés par un agent donné (Dossier.agent -> AgentTradeFinance.id)
    List<Dossier> findByAgentId(Long agentId);

    // Recherche/filtrage par statut ("EN_COURS", "VALIDE", "REJETE"...)
    List<Dossier> findByStatut(String statut);

    // Filtrage combiné : dossiers d'un agent avec un statut précis
    List<Dossier> findByAgentIdAndStatut(Long agentId, String statut);

    boolean existsByReference(String reference);
}