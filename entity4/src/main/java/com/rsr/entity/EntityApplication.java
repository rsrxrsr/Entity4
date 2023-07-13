package com.rsr.entity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
//import org.springframework.context.annotation.ComponentScan;

@ServletComponentScan(basePackages={"com.rsr.logger"})
@SpringBootApplication(scanBasePackages={"com.rsr.entity", "com.rsr.security"})
//@ComponentScan({"com.rsr.entity", "com.rsr.security"})
public class EntityApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntityApplication.class, args);
	}

}
