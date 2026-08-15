package com.tradefinance.trade_finance_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomFichier;

    private String cheminFichier;

    private LocalDateTime dateUpload;

    private String statut;

    @Column(columnDefinition = "TEXT")
    private String texteOcr;

    @ManyToOne
    @JoinColumn(name = "dossier_id")
    private Dossier dossier;

    @ManyToOne
    @JoinColumn(name = "type_document_id")
    private TypeDocument typeDocument;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private List<Analyse> analyses = new ArrayList<>();

    public Document() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public LocalDateTime getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(LocalDateTime dateUpload) {
        this.dateUpload = dateUpload;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getTexteOcr() {
        return texteOcr;
    }

    public void setTexteOcr(String texteOcr) {
        this.texteOcr = texteOcr;
    }

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    public TypeDocument getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(TypeDocument typeDocument) {
        this.typeDocument = typeDocument;
    }

    public List<Analyse> getAnalyses() {
        return analyses;
    }

    public void setAnalyses(List<Analyse> analyses) {
        this.analyses = analyses;
    }
}