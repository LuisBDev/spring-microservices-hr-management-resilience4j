package com.microservices.resilience4j.employee_ms.service.integration.retry;

import com.microservices.resilience4j.employee_ms.client.webclient.DepartmentV1WebClient;
import com.microservices.resilience4j.employee_ms.dto.response.EmployeeResponseDto;
import com.microservices.resilience4j.employee_ms.service.integration.DepartmentIntegrationService;
import io.github.resilience4j.retry.annotation.Retry;
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
public class DepartmentIntegrationServiceRetryImpl implements DepartmentIntegrationService {

    private final DepartmentV1WebClient departmentClient;

    /**
     * Obtiene datos del departamento usando WebClient con Retry para manejar fallos temporales
     * Si el servicio de departamentos está temporalmente no disponible, se reintentará la llamada según la configuración de Retry
     */
    @Override
    @Retry(name = "departmentServiceRetry", fallbackMethod = "getDepartmentFallback")
    public EmployeeResponseDto obtainAndSetDepartmentData(EmployeeResponseDto employeeResponseDto) {

        log.info("Calling Department Service for departmentId: {}", employeeResponseDto.getDepartmentId());

        return Optional.ofNullable(departmentClient.getDepartmentById(employeeResponseDto.getDepartmentId()))
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
     * Método fallback que se ejecuta cuando se agotan los reintentos configurados en el método anotado con @Retry
     * o cuando ocurre una excepción en la llamada al servicio de departamentos
     * La firma del método debe coincidir con la del método original más un
     * parámetro para la excepción
     */
    public EmployeeResponseDto getDepartmentFallback(EmployeeResponseDto employeeResponseDto, Exception exception) {

        log.error("Using RETRY fallback method. Error: {}", exception.getMessage());
        log.warn("Department Service is unavailable. Returning employee data with default department info");

        // Retornar datos del empleado con información por defecto del departamento
        return employeeResponseDto.toBuilder()
                .departmentName("DEPARTMENT UNAVAILABLE")
                .departmentCode("N/A")
                .departmentAddress("Service Temporarily Unavailable")
                .build();
    }


}
