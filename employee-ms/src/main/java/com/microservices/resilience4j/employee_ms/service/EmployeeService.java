package com.microservices.resilience4j.employee_ms.service;

import com.microservices.resilience4j.employee_ms.dto.request.EmployeeRequestDto;
import com.microservices.resilience4j.employee_ms.dto.response.EmployeeResponseDto;

/**
 * @author Luis Balarezo
 **/
public interface EmployeeService {

    EmployeeResponseDto getEmployeeById(String employeeId);

    EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto);
}
