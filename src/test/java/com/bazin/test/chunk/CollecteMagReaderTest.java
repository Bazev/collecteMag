package com.bazin.test.chunk;

import com.bazin.test.models.Client;
import com.bazin.test.models.Compte;
import com.bazin.test.models.CsvFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobScopeTestUtils;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.batch.test.MetaDataInstanceFactory.DEFAULT_JOB_INSTANCE_ID;
import static org.springframework.batch.test.MetaDataInstanceFactory.DEFAULT_JOB_NAME;

@SpringBootTest
class CollecteMagReaderTest {

    @Autowired
    private CollecteMagReader reader;

    @Value("classpath:input.csv")
    private Path collecteFile;

    @TempDir
    private static Path tempDir;

    @Test
    void open() throws Exception {

        //Act
        List<CsvFile> csvFileList = readAll();

        // Assert
        List<Client> clientsValid = new ArrayList<>();
        List<Compte> comptesValid = new ArrayList<>();
        List<Client> clientsError = new ArrayList<>();
        List<Compte> comptesError = new ArrayList<>();
        csvFileList.forEach(c -> {
            clientsValid.addAll(c.getClientList());
            comptesValid.addAll(c.getCompteList());
            clientsError.addAll(c.getClientsError());
            comptesError.addAll(c.getComptesError());
        });

        Assertions.assertFalse(csvFileList.isEmpty());
        Assertions.assertEquals(3, clientsValid.size());
        Assertions.assertEquals(5, comptesValid.size());
        Assertions.assertEquals(2, clientsError.size());
        Assertions.assertTrue(comptesError.isEmpty());

    }

    private List<CsvFile> readAll() throws Exception {

        JobParameters jobParameters = new JobParameters();
        JobExecution jobExecution = MetaDataInstanceFactory.createJobExecution(DEFAULT_JOB_NAME, DEFAULT_JOB_INSTANCE_ID, DEFAULT_JOB_INSTANCE_ID, jobParameters);

        Path input = Files.copy(collecteFile, Paths.get(tempDir.toString(), collecteFile.getFileName().toString()));

        return JobScopeTestUtils.doInJobScope(jobExecution, () -> {
            reader.setResource(new FileSystemResource(input));
            this.reader.open(jobExecution.getExecutionContext());

            List<CsvFile> csvFiles = new ArrayList<>();
            CsvFile csvFile;

            while ((csvFile = this.reader.read()) != null) {
                csvFiles.add(csvFile);
            }
            return csvFiles;
        });
    }
}


