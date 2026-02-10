package com.microservices.resilience4j.department_ms.controller;

import com.microservices.resilience4j.department_ms.dto.response.DepartmentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luis Balarezo
 **/
@RestController
@RequestMapping("/departments")
public class DepartmentRestController {

    @GetMapping("/test")
    public ResponseEntity<Map<String,Object>> testEndpoint() {

        Map<String,Object> map = new HashMap<>();
        map.put("message","Department Service is running!");
        return ResponseEntity.ok(map);

    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable String id,
                                                                @RequestParam(value = "fail", required = false) boolean fail) {

        DepartmentResponse departmentResponse = DepartmentResponse.builder()
                .id(id)
                .name("Department Name for id: " + id)
                .code("DEPT-" + id)
                .address("Department Address for id: " + id)
                .build();

        if (fail) {
            throw new RuntimeException("Simulated failure for department id: " + id);
        }
        return ResponseEntity.ok(departmentResponse);

    }

}
