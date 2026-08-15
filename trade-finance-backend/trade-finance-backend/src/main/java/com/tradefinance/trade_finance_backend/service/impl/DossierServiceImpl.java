package com.tradefinance.trade_finance_backend.service.impl;

import com.tradefinance.trade_finance_backend.dto.DocumentDTO;
import com.tradefinance.trade_finance_backend.dto.DossierDTO;
import com.tradefinance.trade_finance_backend.entity.AgentTradeFinance;
import com.tradefinance.trade_finance_backend.entity.Document;
import com.tradefinance.trade_finance_backend.entity.Dossier;
import com.tradefinance.trade_finance_backend.exception.ResourceNotFoundException;
import com.tradefinance.trade_finance_backend.repository.AgentTradeFinanceRepository;
import com.tradefinance.trade_finance_backend.repository.DossierRepository;
import com.tradefinance.trade_finance_backend.service.DossierService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DossierServiceImpl implements DossierService {

    private final DossierRepository dossierRepository;
    private final AgentTradeFinanceRepository agentRepository;

    public DossierServiceImpl(DossierRepository dossierRepository,
                               AgentTradeFinanceRepository agentRepository) {
        this.dossierRepository = dossierRepository;
        this.agentRepository = agentRepository;
    }

    @Override
    public DossierDTO creer(String reference, String typeOperation, Long agentId) {
        if (dossierRepository.existsByReference(reference)) {
            throw new IllegalArgumentException("Un dossier avec la référence " + reference + " existe déjà");
        }

        AgentTradeFinance agent = agentRepository.findById(agentId)
                .orElseThrow(() -> ResourceNotFoundException.of("AgentTradeFinance", agentId));

        Dossier dossier = new Dossier();
        dossier.setReference(reference);
        dossier.setTypeOperation(typeOperation);
        dossier.setDateCreation(LocalDateTime.now());
        dossier.setStatut("EN_COURS");
        dossier.setAgent(agent);

        Dossier saved = dossierRepository.save(dossier);
        return toDTO(saved, false);
    }

    @Override
    public DossierDTO getById(Long id) {
        Dossier dossier = dossierRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Dossier", id));
        return toDTO(dossier, true);
    }

    @Override
    public List<DossierDTO> getAll() {
        return dossierRepository.findAll().stream()
                .map(d -> toDTO(d, false))
                .collect(Collectors.toList());
    }

    @Override
    public List<DossierDTO> getByAgent(Long agentId) {
        return dossierRepository.findByAgentId(agentId).stream()
                .map(d -> toDTO(d, false))
                .collect(Collectors.toList());
    }

    @Override
    public List<DossierDTO> getByStatut(String statut) {
        return dossierRepository.findByStatut(statut).stream()
                .map(d -> toDTO(d, false))
                .collect(Collectors.toList());
    }

    @Override
    public DossierDTO changerStatut(Long id, String nouveauStatut) {
        Dossier dossier = dossierRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Dossier", id));
        dossier.setStatut(nouveauStatut);
        return toDTO(dossierRepository.save(dossier), false);
    }

    @Override
    public void delete(Long id) {
        if (!dossierRepository.existsById(id)) {
            throw ResourceNotFoundException.of("Dossier", id);
        }
        dossierRepository.deleteById(id);
    }

    private DossierDTO toDTO(Dossier dossier, boolean avecDocuments) {
        AgentTradeFinance agent = dossier.getAgent();
        DossierDTO dto = new DossierDTO(
                dossier.getId(), dossier.getReference(), dossier.getDateCreation(),
                dossier.getStatut(), dossier.getTypeOperation(),
                agent != null ? agent.getId() : null,
                agent != null ? agent.getPrenom() + " " + agent.getNom() : null
        );

        if (avecDocuments && dossier.getDocuments() != null) {
            List<DocumentDTO> documentsDTO = dossier.getDocuments().stream()
                    .map(this::documentToDTO)
                    .collect(Collectors.toList());
            dto.setDocuments(documentsDTO);
        }

        return dto;
    }

    private DocumentDTO documentToDTO(Document document) {
        return new DocumentDTO(
                document.getId(), document.getNomFichier(), document.getCheminFichier(),
                document.getDateUpload(), document.getStatut(),
                document.getTypeDocument() != null ? document.getTypeDocument().getId() : null,
                document.getTypeDocument() != null ? document.getTypeDocument().getNom() : null,
                document.getDossier() != null ? document.getDossier().getId() : null
        );
    }
}