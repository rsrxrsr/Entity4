package com.rsr.security.fix;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.rsr.security.jwt.JwtUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
//  Basic Security Hard Code  
    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
    	System.out.println("****** Estamos Aqui !!!");
    	http
    	   .csrf(csrf -> csrf.disable())
    	   .formLogin(withDefaults()) 
    	   .logout(withDefaults())
    	   .authorizeHttpRequests(authorize -> authorize
    	      //.requestMatchers("/restapi/**").hasAnyAuthority("ADMIN","USER")
    		  //.requestMatchers("/usuario/**").hasAnyAuthority("ADMIN","USER")
    		  //.requestMatchers("/area").permitAll()
       	      //.anyRequest().authenticated()
    		  .requestMatchers("/login").permitAll()
    		  .anyRequest().permitAll())
        ;            	
        return http.build();
    }
//
        
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
//   
    @Bean
    JwtUtil jwtUtil() {
		return new JwtUtil();
	}
//

}
