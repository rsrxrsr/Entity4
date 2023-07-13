package com.rsr.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.rsr.entity.model.FuncionDto;
import com.rsr.entity.repository.IFuncion;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
/*  Security Hard Code  
    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
    	System.out.println("****** Estamos Aqui !!!");
    	http
    	   .csrf(csrf -> csrf.disable())
    	   .formLogin(withDefaults()) 
    	   .logout(withDefaults())
    	   .authorizeHttpRequests(authorize -> authorize
    		  .requestMatchers("/restapi/**").hasAuthority("ADMIN")
    		  .requestMatchers("/usuario/**").hasAuthority("USER")
    		  .requestMatchers("/area").permitAll()
       	      .anyRequest().authenticated())
        ;            	
        return http.build();
    }
 */
//  Security Dynamic Code
    @Autowired
    private IFuncion funcionRepository;

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
    	System.out.println("****** Estamos Aqui !!!");
    	http
    	   .csrf(csrf -> csrf.disable())
    	   .httpBasic(withDefaults())
    	   .formLogin(withDefaults()) 
    	   .logout(withDefaults())
        ;
    	http
 	   		.authorizeHttpRequests(authorize -> { 
 	            funcionRepository.findAll()
 	               .stream().map(FuncionDto::new).collect(Collectors.toList())
 	               .forEach(funcion ->
 	              	   authorize.requestMatchers(funcion.getUrl()).hasAnyAuthority(funcion.getPermisos())
 	               );
 	            authorize.requestMatchers("/area").permitAll();
   			    authorize.anyRequest().authenticated();
 	   		});            	
        return http.build();
    }
   
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
