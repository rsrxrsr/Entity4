package com.rsr.entity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan(basePackages={"com.rsr.logger"})
@SpringBootApplication(scanBasePackages={"com.rsr.entity", "com.rsr.security.basic"})
public class EntityApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntityApplication.class, args);
	}

}
