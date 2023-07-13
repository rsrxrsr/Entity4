package com.rsr.security;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.rsr.entity.repository.IFuncion;
import com.rsr.entity.repository.IUsuario;
import com.rsr.entity.model.FuncionDto;
import com.rsr.entity.model.UsuarioDto;

//@Configuration
//@EnableWebSecurity
public class SecurityConfigBack {

    @Autowired
    private IUsuario userRepository;
    @Autowired
    private IFuncion funcionRepository;

    private List<FuncionDto> funciones;
       
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
    	System.out.println("****** Estamos Aqui??? !!!");
/*    
    	http
            .csrf(withDefaults())
            .cors(withDefaults())
            .formLogin(withDefaults())
            .httpBasic(withDefaults())
            //.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            //.addFilterBefore(new SecurityFilter(), UsernamePasswordAuthenticationFilter.class)  // <= JWT Filter
        ;
*/        
/*
    	http
            .authorizeHttpRequests(authorize -> authorize
            	    .requestMatchers(HttpMethod.GET, "/usuario/login").permitAll()
                    .requestMatchers("/restapi/**").hasRole("ADMIN")
                    .requestMatchers("/usuario/**").hasAnyRole("ADMIN", "USER")
                    .requestMatchers("/area/*").hasAnyRole("OPERATOR")
                    .anyRequest().authenticated()
    	);
*/
 
/*        
    	http
        .authorizeHttpRequests(authorize -> {
        	authorize.requestMatchers("/usuario/login").permitAll();
        	funciones = funcionRepository.findAll().stream().map(FuncionDto::new).collect(Collectors.toList());
        	System.out.println("******"+funciones.size());
//
        	for (FuncionDto funcion : funciones) {
    			authorize.requestMatchers(funcion.getMetodo(), funcion.getUrl()).hasAnyAuthority(funcion.getPermisos());
//    			authorize.requestMatchers(funcion.getUrl()).hasAnyAuthority(funcion.getPermisos());
    		    System.out.println("****** Metodo: "+HttpMethod.GET+" :******** Url: "+funcion.getUrl()+" :****** Permisos: "+funcion.getPermisos());
    		}
//    		
    	});
*/    	
    	http
    	   //.httpBasic(withDefaults())
           //.formLogin(withDefaults())
    	   .authorizeHttpRequests(authorize -> authorize
    		  .requestMatchers("/restapi/**").authenticated()
    		  .requestMatchers("/**").permitAll()
    		//.requestMatchers(HttpMethod.POST, "/usuario/login/**").permitAll()
    		//.requestMatchers("/usuario/login/**").permitAll()
            //.anyRequest().authenticated()
        );
            	
        return http.build();
    }   
    
    @Bean BCryptPasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
 
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
 // Metodo personalizado
    public HttpMethod getMetodo(String metodo) {
    	HttpMethod result=HttpMethod.GET;
    	switch (metodo) {    	
    		case "POST": result=HttpMethod.POST;
    		case "PUT": result=HttpMethod.PUT;
    		case "DELETE": result=HttpMethod.DELETE;
    	}
    	return result;
    }
 */            
}
