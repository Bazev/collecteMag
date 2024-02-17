package com.bazin.test.chunk;

import com.bazin.test.config.JobInfo;
import com.bazin.test.models.CsvFile;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.bazin.test.config.BatchUtils.getProperty;

public class CollecteMagWriterRejet extends FlatFileItemWriter<CsvFile> {

    @Autowired
    private JobInfo jobInfo;

    public CollecteMagWriterRejet() {
        setLineAggregator(new CollecteMagFieldExtractor());

        try {
            String output = getProperty("path.output");
            setResource(new FileSystemResource(output+"fichier_rejet"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void write(Chunk<? extends CsvFile> items) throws Exception {

        List<CsvFile> csvFiles = new ArrayList<>();
        for (CsvFile csvFile : items) {

            if (!csvFile.getClientsError().isEmpty() || !csvFile.getComptesError().isEmpty()) {
                csvFiles.add(csvFile);
            }
        }
        jobInfo.setCountError(csvFiles.size());
        super.write(new Chunk<>(csvFiles));
    }
}
