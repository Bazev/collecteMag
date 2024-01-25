package com.bazin.test.chunk;

import com.bazin.test.models.Client;
import com.bazin.test.models.Compte;
import com.bazin.test.models.CsvFile;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
public class CollecteMagReader extends FlatFileItemReader<CsvFile> {

    private static final String ID_CLIENT = "0";
    private static final String SEPARATEUR = "|";
    private static final String INPUT = "/collecteMag/input/input.csv";


    @Override
    public void afterPropertiesSet() throws Exception {

        final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer(SEPARATEUR);
        final DefaultLineMapper<CsvFile> lineMapper = new DefaultLineMapper<>();

        setResource(new FileSystemResource(INPUT));
        lineMapper.setFieldSetMapper(csvFileFieldSetMapper());
        lineMapper.setLineTokenizer(lineTokenizer);
        setLineMapper(lineMapper);

        super.afterPropertiesSet();
    }

    private FieldSetMapper<CsvFile> csvFileFieldSetMapper() {
        return this::buildCsvFile;
    }

    private CsvFile buildCsvFile(FieldSet fieldSet) {

        List<Client> clientsError = new ArrayList<>();
        List<Client> clientsValid = new ArrayList<>();
        List<Compte> compteList = new ArrayList<>();
        List<Compte> comptesError = new ArrayList<>();

        String firstCarac = String.valueOf(fieldSet.readRawString(0).charAt(0));
        boolean lineIsClient = ID_CLIENT.equals(firstCarac);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        if (lineIsClient) {
            Client client = buildClient(fieldSet);
            Set<ConstraintViolation<Client>> violations = validator.validate(client);

            if (!violations.isEmpty()) {
                log.debug("Le client {} est en rejet", client.getIdentifiant());
                String messageError = buildMessageError(violations);
                client.setMessageError(messageError);
                clientsError.add(client);
            } else {
                clientsValid.add(client);
            }
        } else {
            Compte compte = buildCompte(fieldSet);
            Set<ConstraintViolation<Compte>> violationsCompte = validator.validate(compte);
            if (!violationsCompte.isEmpty()) {
                log.debug("Le compte {} est en rejet", compte.getIdentifiant());
                String messageError = buildMessageCompteError(violationsCompte);
                compte.setMessageError(messageError);
                comptesError.add(compte);

            }
            compteList.add(buildCompte(fieldSet));
        }

        return CsvFile.builder()
                .clientList(clientsValid)
                .clientsError(clientsError)
                .compteList(compteList)
                .comptesError(comptesError)
                .build();
    }

    @NotNull
    private static String buildMessageError(Set<ConstraintViolation<Client>> violations) {
        String messageError = "";
        for (ConstraintViolation<Client> violation : violations) {
            messageError = messageError.concat(violation.getMessage().concat( " - "));
            log.debug(messageError);
        }
        return messageError;
    }

    @NotNull
    private static String buildMessageCompteError(Set<ConstraintViolation<Compte>> violations) {
        String messageError = "";
        for (ConstraintViolation<Compte> violation : violations) {
            messageError = messageError.concat(violation.getMessage().concat( " - "));
            log.debug(messageError);
        }
        return messageError;
    }

    private Client buildClient(FieldSet fieldSet) {

        LocalDate dateNaissance = convertDate(fieldSet.readRawString(4));

        return Client.builder()
                .identifiant(fieldSet.readRawString(0))
                .prenom(fieldSet.readRawString(1))
                .nom(fieldSet.readRawString(2))
                .adresseMail(fieldSet.readRawString(3))
                .dateNaissanceDate(dateNaissance)
                .dateNaissance(dateNaissance.toString())
                .build();
    }

    private Compte buildCompte(FieldSet fieldSet) {
        return Compte.builder()
                .identifiant(fieldSet.readRawString(0))
                .identifiantClient(fieldSet.readRawString(1))
                .solde(fieldSet.readRawString(2))
                .decouvertAutorise(fieldSet.readRawString(3))
                .build();
    }

    private LocalDate convertDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateString, formatter);
    }




}
