package com.bazin.test.chunk;

import com.bazin.test.config.JobInfo;
import com.bazin.test.models.Client;
import com.bazin.test.models.Compte;
import com.bazin.test.models.CsvFile;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonObjectMarshaller;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.bazin.test.config.BatchUtils.getProperty;

public class CollecteMagWriter implements ItemWriter<CsvFile> {

    @Autowired
    private JobInfo jobInfo;


    @Override
    public void write(Chunk<? extends CsvFile> csvFiles) throws Exception {

        List<Client> clientList = new ArrayList<>();
        List<Compte> compteList = new ArrayList<>();

        for (CsvFile csvFile : csvFiles) {

            clientList.addAll(csvFile.getClientList());
            compteList.addAll(csvFile.getCompteList());
        }
        jobInfo.setCountClients(clientList.size());
        jobInfo.setCountComptes(compteList.size());

        writeClients(clientList);
        writeComptes(compteList);
    }



    private void writeClients(List<Client> clients) throws IOException {
        JsonObjectMarshaller<List<Client>> jsonObjectMarshaller = new JacksonJsonObjectMarshaller<>();
        String jsonContent = jsonObjectMarshaller.marshal(clients);
        String output = getProperty("path.output");
        try (FileWriter writer = new FileWriter(output+"clients.json")) {
            writer.write(jsonContent);
        }
    }

    private void writeComptes(List<Compte> comptes) throws IOException {
        JsonObjectMarshaller<List<Compte>> jsonObjectMarshaller = new JacksonJsonObjectMarshaller<>();
        String jsonContent = jsonObjectMarshaller.marshal(comptes);
        String output = getProperty("path.output");
        try (FileWriter fileWriter = new FileWriter(output+"compte.json")){
            fileWriter.write(jsonContent);
        }
    }



}
