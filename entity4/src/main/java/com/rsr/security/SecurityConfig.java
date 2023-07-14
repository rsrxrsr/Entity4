package com.rsr.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.rsr.entity.model.FuncionDto;
import com.rsr.entity.repository.IFuncion;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
/*  Basic Security Hard Code  
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

/*
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
*/
	
	
/*
//  Security Dynamic Code
*/
	@Autowired
    private IFuncion funcionRepository;
    
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
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception {
    	System.out.println("****** Estamos Aqui !!!");
    	http
    	   .cors(withDefaults())
    	   .csrf(csrf -> csrf.disable()) // permite acceso a todos los metodos
           .exceptionHandling(handling -> handling.authenticationEntryPoint((rq, rs, er) -> {
           		rs.sendError(HttpServletResponse.SC_UNAUTHORIZED, er.getMessage());}))
    	   //.httpBasic(withDefaults())
    	   //.formLogin(withDefaults()) 
    	   //.logout(withDefaults())
    	   .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    	   .addFilterBefore(new SecurityFilter(), UsernamePasswordAuthenticationFilter.class)  // <= JWT Filter
        ;
    	http
 	   		.authorizeHttpRequests(authorize -> { 
 	   			authorize.requestMatchers(HttpMethod.GET, "/usuario/login/**").permitAll();
 	            authorize.requestMatchers("/area").permitAll();
 	            funcionRepository.findAll()
 	               .stream().map(FuncionDto::new).collect(Collectors.toList())
 	               .forEach(funcion ->
 	              	   authorize.requestMatchers(funcion.getUrl()).hasAnyAuthority(funcion.getPermisos())
 	               );
   			    authorize.anyRequest().authenticated();
 	   		});            	
        return http.build();
    }
   
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    SecurityKey securityKey() {
		return new SecurityKey();
	}
    
}
