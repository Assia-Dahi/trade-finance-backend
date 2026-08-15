package com.tradefinance.trade_finance_backend.service;

import com.tradefinance.trade_finance_backend.dto.AgentTradeFinanceDTO;
import java.util.List;

public interface AgentTradeFinanceService {

    AgentTradeFinanceDTO creer(AgentTradeFinanceDTO dto, String motDePasseClair);

    AgentTradeFinanceDTO getById(Long id);

    List<AgentTradeFinanceDTO> getAll();

    AgentTradeFinanceDTO update(Long id, AgentTradeFinanceDTO dto);

    void delete(Long id);

    boolean verifierIdentifiants(String email, String motDePasseClair);
}