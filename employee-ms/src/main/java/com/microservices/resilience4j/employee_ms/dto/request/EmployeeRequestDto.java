package com.microservices.resilience4j.employee_ms.dto.request;

import lombok.*;

/**
 * @author Luis Balarezo
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDto {

    private String firstName;
    private String lastName;
    private String email;
    private String departmentId;

}
