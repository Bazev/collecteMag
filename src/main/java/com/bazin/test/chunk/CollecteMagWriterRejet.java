package com.bazin.test.chunk;

import com.bazin.test.models.CsvFile;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.core.io.FileSystemResource;

import java.util.ArrayList;
import java.util.List;

public class CollecteMagWriterRejet extends FlatFileItemWriter<CsvFile> {

    private static final String OUTPUT = "/collecteMag/output/";

    public CollecteMagWriterRejet() {
        setLineAggregator(new CollecteMagFieldExtractor());
        setResource(new FileSystemResource(OUTPUT+"fichier_rejet"));
    }



    @Override
    public void write(Chunk<? extends CsvFile> items) throws Exception {

        List<CsvFile> csvFiles = new ArrayList<>();
        for (CsvFile csvFile : items) {

            if (!csvFile.getClientsError().isEmpty() || !csvFile.getComptesError().isEmpty()) {
                csvFiles.add(csvFile);
            }
        }
        super.write(new Chunk<>(csvFiles));
    }
}
