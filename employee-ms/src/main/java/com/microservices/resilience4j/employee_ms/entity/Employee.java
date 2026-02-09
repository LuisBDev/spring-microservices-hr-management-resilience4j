package com.microservices.resilience4j.employee_ms.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Luis Balarezo
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "employees")
public class Employee {

    @Id
//    @Field("_id")
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String departmentId;

}
