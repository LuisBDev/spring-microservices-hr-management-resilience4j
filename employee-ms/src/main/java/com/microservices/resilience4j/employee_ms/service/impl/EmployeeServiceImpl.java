package com.microservices.resilience4j.employee_ms.service.impl;

import com.microservices.resilience4j.employee_ms.client.feign.config.DepartmentV1FeignClient;
import com.microservices.resilience4j.employee_ms.client.feign.dto.DepartmentResponse;
import com.microservices.resilience4j.employee_ms.dto.request.EmployeeRequestDto;
import com.microservices.resilience4j.employee_ms.dto.response.EmployeeResponseDto;
import com.microservices.resilience4j.employee_ms.entity.Employee;
import com.microservices.resilience4j.employee_ms.mapper.EmployeeMapper;
import com.microservices.resilience4j.employee_ms.repository.EmployeeRepository;
import com.microservices.resilience4j.employee_ms.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    private final DepartmentV1FeignClient departmentClient;


    @Override
    public EmployeeResponseDto getEmployeeById(String employeeId) {

        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);

        EmployeeResponseDto employeeResponseDto = employeeOptional.map(employee -> employeeMapper.toResponseDto(employee))
                .orElseThrow( () -> new RuntimeException("Employee not found with id: " + employeeId));

        EmployeeResponseDto employeeResponseWithDepartmentData = obtainAndSetDepartmentData(employeeResponseDto);

        return employeeResponseWithDepartmentData;

    }

     public EmployeeResponseDto obtainAndSetDepartmentData(EmployeeResponseDto employeeResponseDto) {

        return Optional.ofNullable(departmentClient.getDepartmentById(employeeResponseDto.getDepartmentId()))
                .map(ResponseEntity::getBody)
                .map(departmentData -> {

                    EmployeeResponseDto.EmployeeResponseDtoBuilder builder = employeeResponseDto.toBuilder();

                    builder.departmentName(departmentData.getName());
                    builder.departmentCode(departmentData.getCode());
                    builder.departmentAddress(departmentData.getAddress());

                    return builder.build();

                })
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + employeeResponseDto.getDepartmentId()));
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
