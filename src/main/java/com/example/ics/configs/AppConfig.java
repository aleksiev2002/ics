package com.example.ics.configs;

import com.example.ics.utils.RequestThrottler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public RequestThrottler requestThrottler() {
        return new RequestThrottler();
    }
}
