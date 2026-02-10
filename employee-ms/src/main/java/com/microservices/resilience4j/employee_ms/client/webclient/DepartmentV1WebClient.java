package com.microservices.resilience4j.employee_ms.client.webclient;

import com.microservices.resilience4j.employee_ms.client.webclient.dto.DepartmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Luis Balarezo
 **/
@Component
@RequiredArgsConstructor
public class DepartmentV1WebClient {

    private final WebClient departmentWebClient;

    public DepartmentResponse getDepartmentById(String id) {
        return departmentWebClient.get()
                .uri("/api/v1/departments/{id}", id)
                .retrieve()
                .bodyToMono(DepartmentResponse.class)
                .block();
    }

}
