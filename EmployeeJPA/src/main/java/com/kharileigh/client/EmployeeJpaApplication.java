package com.kharileigh.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.kharileigh")
@EntityScan(basePackages = "com.kharileigh.entity")
@EnableJpaRepositories(basePackages = "com.kharileigh.model.persistence")
public class EmployeeJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeJpaApplication.class, args);
	}

}
