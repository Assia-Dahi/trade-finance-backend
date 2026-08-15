package com.tradefinance.trade_finance_backend.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "resultats_analyse")
public class ResultatAnalyse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean conforme;

    private Double scoreConformite;

    @Column(columnDefinition = "TEXT")
    private String resume;

    @OneToOne
    @JoinColumn(name = "analyse_id")
    private Analyse analyse;

    @OneToMany(mappedBy = "resultatAnalyse", cascade = CascadeType.ALL)
    private List<Anomalie> anomalies = new ArrayList<>();

    public ResultatAnalyse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isConforme() {
        return conforme;
    }

    public void setConforme(boolean conforme) {
        this.conforme = conforme;
    }

    public Double getScoreConformite() {
        return scoreConformite;
    }

    public void setScoreConformite(Double scoreConformite) {
        this.scoreConformite = scoreConformite;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public Analyse getAnalyse() {
        return analyse;
    }

    public void setAnalyse(Analyse analyse) {
        this.analyse = analyse;
    }

    public List<Anomalie> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<Anomalie> anomalies) {
        this.anomalies = anomalies;
    }
}