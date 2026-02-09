package com.microservices.resilience4j.employee_ms.dto.response;

import lombok.*;

/**
 * @author Luis Balarezo
 **/
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDto {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String departmentId;
    private String departmentName;
    private String departmentCode;
    private String departmentAddress;

}
