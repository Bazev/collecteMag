package com.bazin.test.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CsvFile {

    private List<Client> clientList;
    private List<Client> clientsError;

    private List<Compte> compteList;
    private List<Compte> comptesError;

}
