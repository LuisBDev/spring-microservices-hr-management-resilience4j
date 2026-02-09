package com.microservices.resilience4j.employee_ms.client.feign.config;

import com.microservices.resilience4j.employee_ms.client.feign.dto.DepartmentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Luis Balarezo
 **/
@FeignClient(name = "department-ms")
public interface DepartmentV1FeignClient {

    @GetMapping("/api/v1/departments/{departmentId}")
    ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable String departmentId);


}
