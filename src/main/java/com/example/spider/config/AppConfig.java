package com.example.spider.config;

/**
 * @Author 章杰
 * @Date 2024/2/29 18:36
 * @Version 1.0.0
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
