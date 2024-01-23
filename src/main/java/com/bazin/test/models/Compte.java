package com.bazin.test.models;

public class Compte {

    private String identifiant;
    private String identifiantClient;
    private String solde;
    private String decouvertAutorise;

    public Compte() {
    }

    public Compte(String identifiant, String identifiantClient, String solde, String decouvertAutorise) {
        this.identifiant = identifiant;
        this.identifiantClient = identifiantClient;
        this.solde = solde;
        this.decouvertAutorise = decouvertAutorise;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getIdentifiantClient() {
        return identifiantClient;
    }

    public void setIdentifiantClient(String identifiantClient) {
        this.identifiantClient = identifiantClient;
    }

    public String getSolde() {
        return solde;
    }

    public void setSolde(String solde) {
        this.solde = solde;
    }

    public String getDecouvertAutorise() {
        return decouvertAutorise;
    }

    public void setDecouvertAutorise(String decouvertAutorise) {
        this.decouvertAutorise = decouvertAutorise;
    }
}
