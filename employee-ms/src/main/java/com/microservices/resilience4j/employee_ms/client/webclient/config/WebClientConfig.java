package com.microservices.resilience4j.employee_ms.client.webclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Luis Balarezo
 **/
@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient departmentWebClient(
            @Value("${http-clients.internal.department-ms.base-url}") String baseUrl,
            WebClient.Builder webClientBuilder){
        return webClientBuilder
                .clone()
                .baseUrl(baseUrl)
                .build();
    }


}
