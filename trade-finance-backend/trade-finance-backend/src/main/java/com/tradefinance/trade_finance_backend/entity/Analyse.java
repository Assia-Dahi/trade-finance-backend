package com.tradefinance.trade_finance_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "analyses")
public class Analyse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateAnalyse;

    private String statut;

    private String typeAnalyse;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    @OneToOne(mappedBy = "analyse", cascade = CascadeType.ALL)
    private ResultatAnalyse resultat;

    public Analyse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateAnalyse() {
        return dateAnalyse;
    }

    public void setDateAnalyse(LocalDateTime dateAnalyse) {
        this.dateAnalyse = dateAnalyse;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getTypeAnalyse() {
        return typeAnalyse;
    }

    public void setTypeAnalyse(String typeAnalyse) {
        this.typeAnalyse = typeAnalyse;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public ResultatAnalyse getResultat() {
        return resultat;
    }

    public void setResultat(ResultatAnalyse resultat) {
        this.resultat = resultat;
    }
}