package com.bazin.test.config;

import com.bazin.test.chunk.CollecteMagReader;
import com.bazin.test.chunk.CollecteMagWriter;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing()
public class CollecteMagJobConfiguration {





    @Bean
    CollecteMagReader reader() {
        return new CollecteMagReader();
    }

    @Bean
    CollecteMagWriter writer() {
        return new CollecteMagWriter();
    }
}
