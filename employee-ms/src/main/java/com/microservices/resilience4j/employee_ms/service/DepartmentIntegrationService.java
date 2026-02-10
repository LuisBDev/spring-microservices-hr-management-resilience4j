package com.microservices.resilience4j.employee_ms.service;

import com.microservices.resilience4j.employee_ms.dto.response.EmployeeResponseDto;

public interface DepartmentIntegrationService {
    EmployeeResponseDto obtainAndSetDepartmentData(EmployeeResponseDto employeeResponseDto);
}
