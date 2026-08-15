package com.tradefinance.trade_finance_backend.dto;

import java.util.List;

public class ResultatAnalyseDTO {

    private Long id;
    private boolean conforme;
    private Double scoreConformite;
    private String resume;
    private List<AnomalieDTO> anomalies;

    public ResultatAnalyseDTO() {}

    public ResultatAnalyseDTO(Long id, boolean conforme, Double scoreConformite,
                               String resume, List<AnomalieDTO> anomalies) {
        this.id = id;
        this.conforme = conforme;
        this.scoreConformite = scoreConformite;
        this.resume = resume;
        this.anomalies = anomalies;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public boolean isConforme() { return conforme; }
    public void setConforme(boolean conforme) { this.conforme = conforme; }
    public Double getScoreConformite() { return scoreConformite; }
    public void setScoreConformite(Double scoreConformite) { this.scoreConformite = scoreConformite; }
    public String getResume() { return resume; }
    public void setResume(String resume) { this.resume = resume; }
    public List<AnomalieDTO> getAnomalies() { return anomalies; }
    public void setAnomalies(List<AnomalieDTO> anomalies) { this.anomalies = anomalies; }
}