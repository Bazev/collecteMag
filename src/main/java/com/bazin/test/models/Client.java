package com.bazin.test.models;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class Client {

    private String identifiant;
    private String nom;
    private String prenom;

    @Email(message = "Email should be valid")
    private String adresseMail;

    @Past
    private LocalDate dateNaissance;

    public Client() {
    }

    public Client(String identifiant, String nom, String prenom, String adresseMail, LocalDate dateNaissance) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
        this.adresseMail = adresseMail;
        this.dateNaissance = dateNaissance;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }


    @Override
    public String toString() {
        return "Client{" +
                "identifiant='" + identifiant + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresseMail='" + adresseMail + '\'' +
                ", dateNaissance=" + dateNaissance +
                '}';
    }
}
