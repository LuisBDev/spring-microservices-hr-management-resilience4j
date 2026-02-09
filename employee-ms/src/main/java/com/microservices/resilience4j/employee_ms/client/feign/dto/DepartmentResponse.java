package com.microservices.resilience4j.employee_ms.client.feign.dto;

import lombok.Getter;

/**
 * @author Luis Balarezo
 **/
@Getter
public class DepartmentResponse {

    private String id;
    private String name;
    private String code;
    private String address;

}
