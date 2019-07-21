package com.pl.skijumping.batchclient;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchClientConfig {

    private final String url;

    public BatchClientConfig(@Value("${feign.batch.client.url}") String url) {
        this.url = url;
    }

    @Bean
    public BatchClient getBatchClient() {
        return Feign.builder()
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .target(BatchClient.class, url);
    }
}
