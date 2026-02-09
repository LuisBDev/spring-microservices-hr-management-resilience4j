package com.microservices.resilience4j.employee_ms.mapper;

import com.microservices.resilience4j.employee_ms.dto.request.EmployeeRequestDto;
import com.microservices.resilience4j.employee_ms.dto.response.EmployeeResponseDto;
import com.microservices.resilience4j.employee_ms.entity.Employee;
import org.mapstruct.Mapper;

/**
 * @author Luis Balarezo
 **/
@Mapper(componentModel = "spring")
public interface EmployeeMapper {


    EmployeeResponseDto toResponseDto(Employee employee);

    Employee requestToEntity(EmployeeRequestDto employeeRequestDto);
}
