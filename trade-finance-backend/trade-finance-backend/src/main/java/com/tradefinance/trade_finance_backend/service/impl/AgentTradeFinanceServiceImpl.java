package com.tradefinance.trade_finance_backend.service.impl;

import com.tradefinance.trade_finance_backend.dto.AgentTradeFinanceDTO;
import com.tradefinance.trade_finance_backend.entity.AgentTradeFinance;
import com.tradefinance.trade_finance_backend.exception.ResourceNotFoundException;
import com.tradefinance.trade_finance_backend.repository.AgentTradeFinanceRepository;
import com.tradefinance.trade_finance_backend.service.AgentTradeFinanceService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgentTradeFinanceServiceImpl implements AgentTradeFinanceService {

    private final AgentTradeFinanceRepository agentRepository;
    private final PasswordEncoder passwordEncoder;

    public AgentTradeFinanceServiceImpl(
            AgentTradeFinanceRepository agentRepository,
            PasswordEncoder passwordEncoder) {

        this.agentRepository = agentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================
    // CRÉER UN AGENT
    // =========================
    @Override
    public AgentTradeFinanceDTO creer(
            AgentTradeFinanceDTO dto,
            String motDePasseClair) {

        AgentTradeFinance agent = new AgentTradeFinance();

        agent.setMatricule(dto.getMatricule());
        agent.setNom(dto.getNom());
        agent.setPrenom(dto.getPrenom());
        agent.setEmail(dto.getEmail());

        // Si aucun statut n'est fourni, ACTIF par défaut
        agent.setStatut(
                dto.getStatut() != null
                        ? dto.getStatut()
                        : "ACTIF"
        );

        // Ne jamais enregistrer le mot de passe en clair
        agent.setMotDePasse(
                passwordEncoder.encode(motDePasseClair)
        );

        AgentTradeFinance saved =
                agentRepository.save(agent);

        return toDTO(saved);
    }

    // =========================
    // RÉCUPÉRER UN AGENT PAR ID
    // =========================
    @Override
    public AgentTradeFinanceDTO getById(Long id) {

        AgentTradeFinance agent =
                agentRepository.findById(id)
                        .orElseThrow(() ->
                                ResourceNotFoundException.of(
                                        "AgentTradeFinance",
                                        id
                                )
                        );

        return toDTO(agent);
    }

    // =========================
    // RÉCUPÉRER TOUS LES AGENTS
    // =========================
    @Override
    public List<AgentTradeFinanceDTO> getAll() {

        return agentRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // =========================
    // MODIFIER UN AGENT
    // =========================
    @Override
    public AgentTradeFinanceDTO update(
            Long id,
            AgentTradeFinanceDTO dto) {

        AgentTradeFinance agent =
                agentRepository.findById(id)
                        .orElseThrow(() ->
                                ResourceNotFoundException.of(
                                        "AgentTradeFinance",
                                        id
                                )
                        );

        agent.setMatricule(dto.getMatricule());
        agent.setNom(dto.getNom());
        agent.setPrenom(dto.getPrenom());
        agent.setEmail(dto.getEmail());
        agent.setStatut(dto.getStatut());

        AgentTradeFinance updated =
                agentRepository.save(agent);

        return toDTO(updated);
    }

    // =========================
    // SUPPRIMER UN AGENT
    // =========================
    @Override
    public void delete(Long id) {

        if (!agentRepository.existsById(id)) {

            throw ResourceNotFoundException.of(
                    "AgentTradeFinance",
                    id
            );
        }

        agentRepository.deleteById(id);
    }

    // =========================
    // VÉRIFIER LES IDENTIFIANTS
    // =========================
    @Override
    public boolean verifierIdentifiants(
            String email,
            String motDePasseClair) {

        return agentRepository.findByEmail(email)
                .map(agent ->
                        passwordEncoder.matches(
                                motDePasseClair,
                                agent.getMotDePasse()
                        )
                )
                .orElse(false);
    }

    // =========================
    // CONVERSION ENTITY → DTO
    // =========================
    private AgentTradeFinanceDTO toDTO(
            AgentTradeFinance agent) {

        return new AgentTradeFinanceDTO(
                agent.getId(),
                agent.getMatricule(),
                agent.getNom(),
                agent.getPrenom(),
                agent.getEmail(),
                agent.getStatut()
        );
    }
}