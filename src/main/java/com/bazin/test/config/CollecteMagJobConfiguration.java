package com.bazin.test.config;

import com.bazin.test.chunk.CollecteMagReader;
import com.bazin.test.chunk.CollecteMagWriter;
import com.bazin.test.chunk.CollecteMagWriterRejet;
import com.bazin.test.listener.BilanBatch;
import com.bazin.test.models.CsvFile;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:application.properties")
public class CollecteMagJobConfiguration {

    @Bean
    public Job collecteJob(JobRepository jobRepository, Step collecteMagStep) {
        return new JobBuilder("collecteJob", jobRepository)
                .start(collecteMagStep)
                .build();
    }

    @Bean
    public Step collecteMagStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("collecteStep", jobRepository)
                .<CsvFile,CsvFile>chunk(1000, transactionManager)
                .reader(collecteReader())
                .writer(compositeItemWriter())
                .listener(bilanBatch())
                .build();
    }

    @Bean
    CollecteMagReader collecteReader() {
        return new CollecteMagReader();
    }

    @Bean
    public CompositeItemWriter<CsvFile> compositeItemWriter() {

        List<ItemWriter<? super CsvFile>> writers = new ArrayList<>();
        writers.add(collecteWriter());
        writers.add(writerRejet());

        CompositeItemWriter<CsvFile> compositeItemWriter = new CompositeItemWriter<>();
        compositeItemWriter.setDelegates(writers);
        return compositeItemWriter;
    }

    @Bean
    CollecteMagWriterRejet writerRejet() {
        return new CollecteMagWriterRejet();
    }

    @Bean
    CollecteMagWriter collecteWriter() {
        return new CollecteMagWriter();
    }

    @Bean
    BilanBatch bilanBatch() {
        return new BilanBatch();
    }




}
