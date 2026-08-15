package com.tradefinance.trade_finance_backend.controller;

import com.tradefinance.trade_finance_backend.dto.AgentTradeFinanceDTO;
import com.tradefinance.trade_finance_backend.service.AgentTradeFinanceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/agents")
public class AgentTradeFinanceController {

    private final AgentTradeFinanceService agentService;

    public AgentTradeFinanceController(AgentTradeFinanceService agentService) {
        this.agentService = agentService;
    }

    // Body attendu : { "agent": {...AgentTradeFinanceDTO}, "motDePasse": "..." }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgentTradeFinanceDTO creer(@RequestBody CreationAgentRequest requete) {
        return agentService.creer(requete.getAgent(), requete.getMotDePasse());
    }

    @GetMapping("/{id}")
    public AgentTradeFinanceDTO getById(@PathVariable Long id) {
        return agentService.getById(id);
    }

    @GetMapping
    public List<AgentTradeFinanceDTO> getAll() {
        return agentService.getAll();
    }

    @PutMapping("/{id}")
    public AgentTradeFinanceDTO update(@PathVariable Long id, @RequestBody AgentTradeFinanceDTO dto) {
        return agentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        agentService.delete(id);
    }

    // Endpoint simple pour tester les identifiants (l'authentification réelle
    // avec génération de token JWT sera branchée dans la couche config/Security)
    @PostMapping("/login")
    public Map<String, Boolean> login(@RequestBody LoginRequest requete) {
        boolean valide = agentService.verifierIdentifiants(requete.getEmail(), requete.getMotDePasse());
        return Map.of("authentifie", valide);
    }

    // --- Petites classes de requête (pas des DTO métier, juste des enveloppes JSON) ---

    public static class CreationAgentRequest {
        private AgentTradeFinanceDTO agent;
        private String motDePasse;

        public AgentTradeFinanceDTO getAgent() { return agent; }
        public void setAgent(AgentTradeFinanceDTO agent) { this.agent = agent; }
        public String getMotDePasse() { return motDePasse; }
        public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    }

    public static class LoginRequest {
        private String email;
        private String motDePasse;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getMotDePasse() { return motDePasse; }
        public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
    }
}