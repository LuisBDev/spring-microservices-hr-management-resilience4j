package com.microservices.resilience4j.employee_ms.controller;

import com.microservices.resilience4j.employee_ms.dto.request.EmployeeRequestDto;
import com.microservices.resilience4j.employee_ms.dto.response.EmployeeResponseDto;
import com.microservices.resilience4j.employee_ms.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luis Balarezo
 **/
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @GetMapping("/test")
    public ResponseEntity<Map<String,Object>> testEndpoint() {

        Map<String,Object> map = new HashMap<>();
        map.put("message","Employee Service is running!");
        return ResponseEntity.ok(map);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable String id) {
        EmployeeResponseDto employeeResponseDto = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeeResponseDto);
    }

    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(@Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        EmployeeResponseDto createdEmployee = employeeService.createEmployee(employeeRequestDto);
        return ResponseEntity.ok(createdEmployee);
    }


}
