package com.bazin.test.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Compte {

    @Size(min = 15, max = 15, message = "L'identifiant doit être sur 15 caractères")
    private String identifiant;
    @Size(min = 10, max = 10, message = "L'identifiant client doit être sur 15 caractères")
    private String identifiantClient;
    private String solde;
    private String decouvertAutorise;

    @JsonIgnore
    private String messageError;

}
