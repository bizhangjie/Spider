package com.example.spider.config;

/**
 * @Author 章杰
 * @Date 2024/2/29 15:18
 * @Version 1.0.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Configuration
@PropertySource("classpath:application.yml")
@EnableConfigurationProperties
public class ServerConfig {

    @Autowired
    private Environment environment;

    @Value("${server.port}")
    private int serverPort;

    @Value("${app.base-url}")
    private String baseUrl;

    @PostConstruct
    public void init() {
        String contextPath = environment.getProperty("server.servlet.context-path", "");
        String Url = baseUrl + ":" + serverPort + contextPath;
        System.out.println("Application is running at: " + Url);
    }
}

