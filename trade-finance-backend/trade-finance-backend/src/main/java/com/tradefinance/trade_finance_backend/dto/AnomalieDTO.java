package com.tradefinance.trade_finance_backend.dto;

public class AnomalieDTO {

    private Long id;
    private String type;
    private String description;
    private String gravite;
    private String champConcerne;

    public AnomalieDTO() {}

    public AnomalieDTO(Long id, String type, String description, String gravite, String champConcerne) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.gravite = gravite;
        this.champConcerne = champConcerne;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getGravite() { return gravite; }
    public void setGravite(String gravite) { this.gravite = gravite; }
    public String getChampConcerne() { return champConcerne; }
    public void setChampConcerne(String champConcerne) { this.champConcerne = champConcerne; }
}