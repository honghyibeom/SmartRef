package com.hong.smartref.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class ExternalApiConfig {
    @Bean
    public RestClient recipeRestClient(
            @Value("${external-api.api-key}") String apiKey
    ) {
        return RestClient.builder()
                .baseUrl("https://w4bwrqmrv6.execute-api.ap-northeast-2.amazonaws.com") // 외부 API 기본 주소
                .defaultHeader("x-api-key", apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
