package com.rsr.entity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.web.servlet.ServletComponentScan;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
/*
//@ServletComponentScan(basePackages={})
@ComponentScan(basePackages={
		"com.rsr.entity",
		"com.rsr.logger",
		"com.rsr.security.fix"
})
*/
@SpringBootApplication(scanBasePackages={ 
		"com.rsr.entity",
		"com.rsr.logger",
		"com.rsr.file",
		"com.rsr.email",
		"com.rsr.security.fix"
		//"com.rsr.security.basic"
		//"com.rsr.security.jwt"
		//}, scanBasePackageClasses={com.rsr.security.jwt.JwtUtil.class
		//}, scanBasePackageClasses={com.rsr.entity.repository.FileRepository.class
		})
@EntityScan(basePackages={"com.rsr.entity","com.rsr.logger","com.rsr.file"})
@EnableJpaRepositories(basePackages={"com.rsr.entity","com.rsr.logger","com.rsr.file"})
//
public class EntityApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntityApplication.class, args);
	}

}
