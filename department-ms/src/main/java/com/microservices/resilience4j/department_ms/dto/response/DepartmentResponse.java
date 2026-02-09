package com.microservices.resilience4j.department_ms.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Luis Balarezo
 **/
@Getter
@Setter
@Builder(toBuilder = true)
public class DepartmentResponse {

    private String id;
    private String name;
    private String code;
    private String address;

}
