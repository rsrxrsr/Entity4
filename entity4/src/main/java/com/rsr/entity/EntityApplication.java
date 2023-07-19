package com.rsr.entity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import org.springframework.boot.web.servlet.ServletComponentScan;
//@ServletComponentScan(basePackages={"com.rsr.logger"})
//@SpringBootApplication(scanBasePackages={"com.rsr.entity", "com.rsr.logger", "com.rsr.security.basic"})
//	scanBasePackageClasses={com.rsr.security.jwt.JwtUtil.class})
@SpringBootApplication(scanBasePackages={"com.rsr.entity", "com.rsr.logger", "com.rsr.security.fix"})
//@SpringBootApplication(scanBasePackages={"com.rsr.entity", "com.rsr.logger", "com.rsr.security.basic"})
//@SpringBootApplication(scanBasePackages={"com.rsr.entity", "com.rsr.logger", "com.rsr.security.jwt"})
public class EntityApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntityApplication.class, args);
	}

}
