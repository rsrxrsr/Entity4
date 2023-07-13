package com.rsr.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

//import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet Filter extends OncePerRequestFilter 
 */
//@Order(value=1)
@WebFilter("/securityFilter")
public class SecurityFilter  extends OncePerRequestFilter  {
	
	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";
	private SecurityKey securityKey = new SecurityKey();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws ServletException, IOException  {
		try {
			System.out.println("*** SecurityFilter");
			if (existeJWTToken(request)) {
				System.out.println("*** existeToken");
				Claims claims = validateToken(request);
				if (claims.get("authorities") != null) {
					setUpSpringAuthentication(claims);
					System.out.println("*** Claims: "+claims);
				} else {
					SecurityContextHolder.clearContext();
				}
			} else {
					SecurityContextHolder.clearContext();
			}
			System.out.println("*** SecurityFilter doFilter()");
			chain.doFilter(request, response);
		} catch (UnsupportedJwtException | MalformedJwtException e) {
			System.out.println("*** Reject");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
		}
	}
	
	private boolean existeJWTToken(HttpServletRequest request) {
		return request.getHeader(HEADER) != null &&
			   request.getHeader(HEADER).startsWith(PREFIX);
	}

	private Claims validateToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		return  (Claims) Jwts.parserBuilder().setSigningKey(securityKey.getSecretKey()).build().parse(jwtToken).getBody();
//		return  Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
	}

	/**
	 * Metodo para autenticarnos dentro del flujo de Spring
	 * @param claims
	 */
	private void setUpSpringAuthentication(Claims claims) {
		//
		//claims.get("authorities", List<String> requiredType);
        //List<String> authorities = (List<String>) claims.get("authorities");
		//UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
		//		claims.getSubject(), null,
		//		authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		
		System.out.println(claims.get("authorities"));
		Collection<? extends GrantedAuthority> authorities = 
				Arrays.stream(claims.get("authorities").toString().split(","))
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());		
		System.out.println(authorities);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				claims.getSubject(), null, authorities);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println(authentication);
	}
			
}
