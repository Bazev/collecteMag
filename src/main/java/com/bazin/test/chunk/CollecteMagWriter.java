package com.bazin.test.chunk;

import com.bazin.test.models.Client;
import com.bazin.test.models.Compte;
import com.bazin.test.models.CsvFile;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonObjectMarshaller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CollecteMagWriter implements ItemWriter<CsvFile> {

    private static final String OUTPUT = "/collecteMag/output/";

    @Override
    public void write(Chunk<? extends CsvFile> csvFiles) throws Exception {

        List<Client> clientList = new ArrayList<>();
        List<Compte> compteList = new ArrayList<>();

        for (CsvFile csvFile : csvFiles) {

            clientList.addAll(csvFile.getClientList());
            compteList.addAll(csvFile.getCompteList());
        }
        writeClients(clientList);
        writeComptes(compteList);
    }



    private void writeClients(List<Client> clients) throws IOException {
        JsonObjectMarshaller<List<Client>> jsonObjectMarshaller = new JacksonJsonObjectMarshaller<>();
        String jsonContent = jsonObjectMarshaller.marshal(clients);
        try (FileWriter writer = new FileWriter(OUTPUT+"clients.json")) {
            writer.write(jsonContent);
        }
    }

    private void writeComptes(List<Compte> comptes) throws IOException {
        JsonObjectMarshaller<List<Compte>> jsonObjectMarshaller = new JacksonJsonObjectMarshaller<>();
        String jsonContent = jsonObjectMarshaller.marshal(comptes);
        try (FileWriter fileWriter = new FileWriter(OUTPUT+"compte.json")){
            fileWriter.write(jsonContent);
        }
    }



}
