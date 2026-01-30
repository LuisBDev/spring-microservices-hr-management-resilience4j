package com.microservices.resilience4j.employee_ms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luis Balarezo
 **/
@RestController
@RequestMapping("/employees")
public class EmployeeRestController {

    @GetMapping("/test")
    public ResponseEntity<Map<String,Object>> testEndpoint() {

        Map<String,Object> map = new HashMap<>();
        map.put("message","Employee Service is running!");
        return ResponseEntity.ok(map);

    }


}
