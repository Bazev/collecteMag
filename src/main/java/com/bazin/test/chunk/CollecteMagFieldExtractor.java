package com.bazin.test.chunk;

import com.bazin.test.models.Client;
import com.bazin.test.models.CsvFile;
import org.jetbrains.annotations.NotNull;
import org.springframework.batch.item.file.transform.LineAggregator;

import java.util.List;

public class CollecteMagFieldExtractor implements LineAggregator<CsvFile> {

    @NotNull
    @Override
    public String aggregate(CsvFile item) {

        List<Client> clientList = item.getClientsError();
        StringBuilder stringBuilder = new StringBuilder();

        for (Client client : clientList ) {
            String line = String.format(
                        "%.10s = " +
                        "%s;",
                        client.getIdentifiant(),
                        client.getMessageError()
                );
                stringBuilder.append(line);

        }
        return stringBuilder.toString().trim();

    }
}
