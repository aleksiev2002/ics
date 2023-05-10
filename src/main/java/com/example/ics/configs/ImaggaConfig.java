package com.example.ics.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImaggaConfig {

    @Value("${imagga.apiKey}")
    private String apiKey;

    @Value("${imagga.secretKey}")
    private String secretKey;

    @Value("${imagga.endpointUrl}")
    private String endpointUrl;

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
