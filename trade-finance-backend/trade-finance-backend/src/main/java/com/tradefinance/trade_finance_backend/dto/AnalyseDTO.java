package com.tradefinance.trade_finance_backend.dto;

import java.time.LocalDateTime;

public class AnalyseDTO {

    private Long id;
    private LocalDateTime dateAnalyse;
    private String statut;
    private String typeAnalyse;
    private Long documentId;
    private String documentNom;
    private ResultatAnalyseDTO resultat;

    public AnalyseDTO() {}

    public AnalyseDTO(Long id, LocalDateTime dateAnalyse, String statut, String typeAnalyse,
                       Long documentId, String documentNom, ResultatAnalyseDTO resultat) {
        this.id = id;
        this.dateAnalyse = dateAnalyse;
        this.statut = statut;
        this.typeAnalyse = typeAnalyse;
        this.documentId = documentId;
        this.documentNom = documentNom;
        this.resultat = resultat;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDateAnalyse() { return dateAnalyse; }
    public void setDateAnalyse(LocalDateTime dateAnalyse) { this.dateAnalyse = dateAnalyse; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public String getTypeAnalyse() { return typeAnalyse; }
    public void setTypeAnalyse(String typeAnalyse) { this.typeAnalyse = typeAnalyse; }
    public Long getDocumentId() { return documentId; }
    public void setDocumentId(Long documentId) { this.documentId = documentId; }
    public String getDocumentNom() { return documentNom; }
    public void setDocumentNom(String documentNom) { this.documentNom = documentNom; }
    public ResultatAnalyseDTO getResultat() { return resultat; }
    public void setResultat(ResultatAnalyseDTO resultat) { this.resultat = resultat; }
}