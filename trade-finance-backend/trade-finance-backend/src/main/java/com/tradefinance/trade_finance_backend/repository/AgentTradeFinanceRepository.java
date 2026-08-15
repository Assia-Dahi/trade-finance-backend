package com.tradefinance.trade_finance_backend.repository;

import com.tradefinance.trade_finance_backend.entity.AgentTradeFinance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AgentTradeFinanceRepository extends JpaRepository<AgentTradeFinance, Long> {

    Optional<AgentTradeFinance> findByEmail(String email);

    Optional<AgentTradeFinance> findByMatricule(String matricule);

    boolean existsByEmail(String email);
}