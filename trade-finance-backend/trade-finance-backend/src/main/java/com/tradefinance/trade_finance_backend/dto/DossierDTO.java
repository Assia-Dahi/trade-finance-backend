package com.tradefinance.trade_finance_backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class DossierDTO {

    private Long id;
    private String reference;
    private LocalDateTime dateCreation;
    private String statut;
    private String typeOperation;
    private Long agentId;
    private String agentNomComplet;
    private List<DocumentDTO> documents;

    public DossierDTO() {}

    public DossierDTO(Long id, String reference, LocalDateTime dateCreation, String statut,
                       String typeOperation, Long agentId, String agentNomComplet) {
        this.id = id;
        this.reference = reference;
        this.dateCreation = dateCreation;
        this.statut = statut;
        this.typeOperation = typeOperation;
        this.agentId = agentId;
        this.agentNomComplet = agentNomComplet;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public String getTypeOperation() { return typeOperation; }
    public void setTypeOperation(String typeOperation) { this.typeOperation = typeOperation; }
    public Long getAgentId() { return agentId; }
    public void setAgentId(Long agentId) { this.agentId = agentId; }
    public String getAgentNomComplet() { return agentNomComplet; }
    public void setAgentNomComplet(String agentNomComplet) { this.agentNomComplet = agentNomComplet; }
    public List<DocumentDTO> getDocuments() { return documents; }
    public void setDocuments(List<DocumentDTO> documents) { this.documents = documents; }
}