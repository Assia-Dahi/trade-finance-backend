package com.tradefinance.trade_finance_backend.dto;

public class AgentTradeFinanceDTO {

    private Long id;
    private String matricule;
    private String nom;
    private String prenom;
    private String email;
    private String statut;
    // Pas de motDePasse ici : ne jamais renvoyer un mot de passe au client, même hashé

    public AgentTradeFinanceDTO() {}

    public AgentTradeFinanceDTO(Long id, String matricule, String nom, String prenom, String email, String statut) {
        this.id = id;
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.statut = statut;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}