package com.microservices.resilience4j.employee_ms.service.impl;

import com.microservices.resilience4j.employee_ms.dto.request.EmployeeRequestDto;
import com.microservices.resilience4j.employee_ms.dto.response.EmployeeResponseDto;
import com.microservices.resilience4j.employee_ms.entity.Employee;
import com.microservices.resilience4j.employee_ms.mapper.EmployeeMapper;
import com.microservices.resilience4j.employee_ms.repository.EmployeeRepository;
import com.microservices.resilience4j.employee_ms.service.DepartmentIntegrationService;
import com.microservices.resilience4j.employee_ms.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Luis Balarezo
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final DepartmentIntegrationService departmentIntegrationService;

    @Override
    public EmployeeResponseDto getEmployeeById(String employeeId) {

        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);

        EmployeeResponseDto employeeResponseDto = employeeOptional.map(employeeMapper::toResponseDto)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + employeeId));

        // Llamada con Circuit Breaker via Integration Service
        return departmentIntegrationService.obtainAndSetDepartmentData(employeeResponseDto);
    }

    @Override
    public EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto) {

        // check if departmentId of employeeRequestDto exists in Department Service

        Employee employeeEntity = employeeMapper.requestToEntity(employeeRequestDto);

        Employee savedEntity = employeeRepository.save(employeeEntity);

        return employeeMapper.toResponseDto(savedEntity);
    }
}
