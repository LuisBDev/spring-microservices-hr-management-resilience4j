package com.microservices.resilience4j.employee_ms.repository;

import com.microservices.resilience4j.employee_ms.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Luis Balarezo
 **/
@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
}
