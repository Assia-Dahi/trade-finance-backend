package com.tradefinance.trade_finance_backend.ai;

import java.util.ArrayList;
import java.util.List;

/**
 * Résultat brut produit par le module IA à partir du texte OCR d'un document.
 * Ce n'est pas une entité JPA : c'est un objet de transport interne,
 * utilisé par AnalyseService pour construire ResultatAnalyse + Anomalie.
 */
public class AIAnalysisResult {

    private boolean conforme;
    private Double scoreConformite;
    private String resume;
    private List<AnomalieDetectee> anomalies = new ArrayList<>();

    public static class AnomalieDetectee {
        private String type;
        private String description;
        private String gravite;
        private String champConcerne;

        public AnomalieDetectee(String type, String description, String gravite, String champConcerne) {
            this.type = type;
            this.description = description;
            this.gravite = gravite;
            this.champConcerne = champConcerne;
        }

        public String getType() { return type; }
        public String getDescription() { return description; }
        public String getGravite() { return gravite; }
        public String getChampConcerne() { return champConcerne; }
    }

    public boolean isConforme() { return conforme; }
    public void setConforme(boolean conforme) { this.conforme = conforme; }

    public Double getScoreConformite() { return scoreConformite; }
    public void setScoreConformite(Double scoreConformite) { this.scoreConformite = scoreConformite; }

    public String getResume() { return resume; }
    public void setResume(String resume) { this.resume = resume; }

    public List<AnomalieDetectee> getAnomalies() { return anomalies; }
    public void setAnomalies(List<AnomalieDetectee> anomalies) { this.anomalies = anomalies; }
}