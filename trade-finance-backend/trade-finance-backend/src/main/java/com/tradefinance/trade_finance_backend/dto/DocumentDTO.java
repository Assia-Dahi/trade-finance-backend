package com.tradefinance.trade_finance_backend.dto;

import java.time.LocalDateTime;

public class DocumentDTO {

    private Long id;
    private String nomFichier;
    private String cheminFichier;
    private LocalDateTime dateUpload;
    private String statut;
    private Long typeDocumentId;
    private String typeDocumentNom;
    private Long dossierId;

    public DocumentDTO() {}

    public DocumentDTO(Long id, String nomFichier, String cheminFichier, LocalDateTime dateUpload,
                        String statut, Long typeDocumentId, String typeDocumentNom, Long dossierId) {
        this.id = id;
        this.nomFichier = nomFichier;
        this.cheminFichier = cheminFichier;
        this.dateUpload = dateUpload;
        this.statut = statut;
        this.typeDocumentId = typeDocumentId;
        this.typeDocumentNom = typeDocumentNom;
        this.dossierId = dossierId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomFichier() { return nomFichier; }
    public void setNomFichier(String nomFichier) { this.nomFichier = nomFichier; }
    public String getCheminFichier() { return cheminFichier; }
    public void setCheminFichier(String cheminFichier) { this.cheminFichier = cheminFichier; }
    public LocalDateTime getDateUpload() { return dateUpload; }
    public void setDateUpload(LocalDateTime dateUpload) { this.dateUpload = dateUpload; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public Long getTypeDocumentId() { return typeDocumentId; }
    public void setTypeDocumentId(Long typeDocumentId) { this.typeDocumentId = typeDocumentId; }
    public String getTypeDocumentNom() { return typeDocumentNom; }
    public void setTypeDocumentNom(String typeDocumentNom) { this.typeDocumentNom = typeDocumentNom; }
    public Long getDossierId() { return dossierId; }
    public void setDossierId(Long dossierId) { this.dossierId = dossierId; }
}