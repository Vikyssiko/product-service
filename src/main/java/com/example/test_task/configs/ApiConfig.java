package com.example.test_task.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ApiConfig {
    @Value("${api.key}")
    private String apiKey;
}
