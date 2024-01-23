package com.bazin.test.chunk;

import com.bazin.test.models.Client;
import com.bazin.test.models.Compte;
import com.bazin.test.models.CsvFile;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.core.io.FileSystemResource;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CollecteMagReader extends FlatFileItemReader<CsvFile> {

    private static final String ID_CLIENT = "0";
    private static final String SEPARATEUR = "|";
    private static final String INPUT = "C:\\collecteMag\\input\\";


    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {

        Path inputFile = Paths.get(INPUT);
        setResource(new FileSystemResource(inputFile));
        setEncoding(StandardCharsets.UTF_8.name());

        super.open(executionContext);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(SEPARATEUR);
        final DefaultLineMapper<CsvFile> lineMapper = new DefaultLineMapper<>();

        lineMapper.setFieldSetMapper(csvFileFieldSetMapper());
        lineMapper.setLineTokenizer(lineTokenizer);
        setLineMapper(lineMapper);

        super.afterPropertiesSet();
    }

    private FieldSetMapper<CsvFile> csvFileFieldSetMapper() {
        return this::buildCsvFile;
    }

    @NotNull
    private CsvFile buildCsvFile(FieldSet fieldSet) {
        CsvFile csvFile = new CsvFile();
        List<Client> clientList = new ArrayList<>();
        List<Compte> compteList = new ArrayList<>();

        String firstCarac = String.valueOf(fieldSet.readRawString(0).charAt(0));
        boolean lineIsClient = ID_CLIENT.equals(firstCarac);

        if (lineIsClient) {
            clientList.add(buildClient(fieldSet));
        } else {
            compteList.add(buildCompte(fieldSet));
        }

        csvFile.setClientList(clientList);
        csvFile.setCompteList(compteList);

        return csvFile;
    }

    private Client buildClient(FieldSet fieldSet) {
        Client client = new Client();
        client.setIdentifiant(fieldSet.readRawString(0));
        client.setPrenom(fieldSet.readRawString(1));
        client.setNom(fieldSet.readRawString(2));
        client.setAdresseMail(fieldSet.readRawString(3));
        client.setDateNaissance(convertDate(fieldSet.readDate(4)));
        return client;
    }

    private Compte buildCompte(FieldSet fieldSet) {
        Compte compte = new Compte();
        compte.setIdentifiant(fieldSet.readRawString(1));
        compte.setIdentifiantClient(fieldSet.readRawString(2));
        compte.setSolde(fieldSet.readRawString(3));
        compte.setDecouvertAutorise(fieldSet.readRawString(4));
        return compte;
    }

    private LocalDate convertDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }




}
