package com.microservices.resilience4j.employee_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EmployeeMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeMsApplication.class, args);
	}

}
