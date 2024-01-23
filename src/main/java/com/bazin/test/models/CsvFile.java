package com.bazin.test.models;

import java.util.List;

public class CsvFile {

    private List<Client> clientList;

    private List<Compte> compteList;

    public CsvFile() {
    }

    public CsvFile(List<Client> clientList, List<Compte> compteList) {
        this.clientList = clientList;
        this.compteList = compteList;
    }

    public List<Client> getClientList() {
        return clientList;
    }

    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

    public List<Compte> getCompteList() {
        return compteList;
    }

    public void setCompteList(List<Compte> compteList) {
        this.compteList = compteList;
    }

    @Override
    public String toString() {
        return "CsvFile{" +
                "clientList=" + clientList +
                ", compteList=" + compteList +
                '}';
    }
}
