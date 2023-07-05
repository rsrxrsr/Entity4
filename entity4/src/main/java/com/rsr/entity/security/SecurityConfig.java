package com.rsr.entity.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.rsr.entity.repository.IUsuario;
import com.rsr.entity.model.UsuarioDto;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private IUsuario userRepository;
    
    @Bean CorsConfigurationSource corsConfigurationSource() {
    	CorsConfiguration configuration = new CorsConfiguration();
    	configuration.setAllowedOrigins(Arrays.asList("*"));
    	configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
    	configuration.setAllowedHeaders(Arrays.asList("*"));
    	configuration.applyPermitDefaultValues();
    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); 
    	source.registerCorsConfiguration("/**",configuration);
    	return source;
    }
    
    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/restapi/**").hasRole("ADMIN")
                        .requestMatchers("/usuario/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/area/*", getMatcher()).hasAnyRole("OPERATOR")
                        .anyRequest().authenticated())
                .formLogin(withDefaults())
    		;
    	return http.build();
    }

    @Bean BCryptPasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
        	UsuarioDto user = new UsuarioDto(userRepository.findByUsuario(username)
        				.orElseThrow(() -> new UsernameNotFoundException("Could not find the user: " + username)));
        	System.out.println("*** Usuario: "+username+" Password: " + user.getPassword()+" Permisos: "+user.getPermisos());
            return new org.springframework.security.core.userdetails.User(
            		user.getUsuario(), passwordEncoder().encode(user.getPassword()),
                    true, true, true, true,
                    AuthorityUtils.createAuthorityList(user.getPermisos()));
        });
    }
    
    public String getMatcher() {
    	return "/area/jefatura/2";
    }
    
}
