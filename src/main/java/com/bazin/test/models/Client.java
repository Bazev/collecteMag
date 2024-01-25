package com.bazin.test.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Client {

    @Size(min = 10, max = 10, message = "L'identifiant doit être sur 15 caractères")
    private String identifiant;
    private String nom;
    private String prenom;

    @Email(message = "L'email doit être valide")
    private String adresseMail;

    @JsonIgnore
    @Past(message = "La date de naissance doit être antérieur à la date du jour")
    private LocalDate dateNaissanceDate;

    private String dateNaissance;

    @JsonIgnore
    private String messageError;




}
