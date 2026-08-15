package com.tradefinance.trade_finance_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "anomalies")
public class Anomalie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String gravite;

    private String champConcerne;

    @ManyToOne
    @JoinColumn(name = "resultat_analyse_id")
    private ResultatAnalyse resultatAnalyse;

    public Anomalie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGravite() {
        return gravite;
    }

    public void setGravite(String gravite) {
        this.gravite = gravite;
    }

    public String getChampConcerne() {
        return champConcerne;
    }

    public void setChampConcerne(String champConcerne) {
        this.champConcerne = champConcerne;
    }

    public ResultatAnalyse getResultatAnalyse() {
        return resultatAnalyse;
    }

    public void setResultatAnalyse(ResultatAnalyse resultatAnalyse) {
        this.resultatAnalyse = resultatAnalyse;
    }
}