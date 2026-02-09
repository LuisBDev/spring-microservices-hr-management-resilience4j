package com.microservices.resilience4j.employee_ms.service.impl;

import com.microservices.resilience4j.employee_ms.dto.request.EmployeeRequestDto;
import com.microservices.resilience4j.employee_ms.dto.response.EmployeeResponseDto;
import com.microservices.resilience4j.employee_ms.entity.Employee;
import com.microservices.resilience4j.employee_ms.mapper.EmployeeMapper;
import com.microservices.resilience4j.employee_ms.repository.EmployeeRepository;
import com.microservices.resilience4j.employee_ms.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Luis Balarezo
 **/
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;


    @Override
    public EmployeeResponseDto getEmployeeById(String employeeId) {

        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);

        EmployeeResponseDto employeeResponseDto = employeeOptional.map(employee -> employeeMapper.toResponseDto(employee))
                .orElseThrow( () -> new RuntimeException("Employee not found with id: " + employeeId));

        //TODO: Call Department Service to get department detail

        return employeeResponseDto;

    }

    @Override
    public EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto) {

        //check if departmentId of employeeRequestDto exists in Department Service

        Employee employeeEntity = employeeMapper.requestToEntity(employeeRequestDto);

        Employee savedEntity = employeeRepository.save(employeeEntity);

        EmployeeResponseDto responseDto = employeeMapper.toResponseDto(savedEntity);

//        responseDto.setDepartmentName("Default Department Name"); //TODO: Setear con un rest client

        return responseDto;
    }
}
