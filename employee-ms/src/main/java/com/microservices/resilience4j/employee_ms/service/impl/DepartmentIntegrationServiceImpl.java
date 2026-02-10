package com.microservices.resilience4j.employee_ms.service.impl;

import com.microservices.resilience4j.employee_ms.client.feign.config.DepartmentV1FeignClient;
import com.microservices.resilience4j.employee_ms.dto.response.EmployeeResponseDto;
import com.microservices.resilience4j.employee_ms.service.DepartmentIntegrationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentIntegrationServiceImpl implements DepartmentIntegrationService {

    private final DepartmentV1FeignClient departmentClient;

    /**
     * Obtiene datos del departamento usando Feign Client con Circuit Breaker
     * Si el servicio de departamentos falla, se ejecuta el método de fallback
     */
    @Override
    @CircuitBreaker(name = "departmentService", fallbackMethod = "getDepartmentFallback")
    public EmployeeResponseDto obtainAndSetDepartmentData(EmployeeResponseDto employeeResponseDto) {

        log.info("Calling Department Service for departmentId: {}", employeeResponseDto.getDepartmentId());

        return Optional.ofNullable(departmentClient.getDepartmentById(employeeResponseDto.getDepartmentId()))
                .map(ResponseEntity::getBody)
                .map(departmentData -> {

                    EmployeeResponseDto.EmployeeResponseDtoBuilder builder = employeeResponseDto.toBuilder();

                    builder.departmentName(departmentData.getName());
                    builder.departmentCode(departmentData.getCode());
                    builder.departmentAddress(departmentData.getAddress());

                    return builder.build();

                })
                .orElseThrow(() -> new RuntimeException(
                        "Department not found with id: " + employeeResponseDto.getDepartmentId()));
    }

    /**
     * Método fallback que se ejecuta cuando el Circuit Breaker está OPEN
     * o cuando ocurre una excepción en la llamada al servicio de departamentos
     * La firma del método debe coincidir con la del método original más un
     * parámetro para la excepción
     */
    public EmployeeResponseDto getDepartmentFallback(EmployeeResponseDto employeeResponseDto, Exception exception) {

        log.error("Using fallback method. Error: {}", exception.getMessage());
        log.warn("Department Service is unavailable. Returning employee data with default department info");

        // Retornar datos del empleado con información por defecto del departamento
        return employeeResponseDto.toBuilder()
                .departmentName("DEPARTMENT UNAVAILABLE")
                .departmentCode("N/A")
                .departmentAddress("Service Temporarily Unavailable")
                .build();
    }
}
