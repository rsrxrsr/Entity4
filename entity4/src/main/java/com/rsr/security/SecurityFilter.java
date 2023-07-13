package com.rsr.security;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

//import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
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
	private final String SECRET = getProperty("jwt.key");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
		throws ServletException, IOException  {
		try {
			System.out.println("*** Security inFilter");
			if (existeJWTToken(request)) {
				System.out.println("*** Security Filter inClaims()");
				Claims claims = validateToken(request);
				if (claims.get("authorities") != null) {
					setUpSpringAuthentication(claims);
					System.out.println("*** Security Filter setHeader()");
				} else {
					//SecurityContextHolder.clearContext();
				}
			} else {
					//SecurityContextHolder.clearContext();
			}
			System.out.println("*** Security Filter doFilter()");
			chain.doFilter(request, response);
		} catch (UnsupportedJwtException | MalformedJwtException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			//return;
		}
	}
	
	private boolean existeJWTToken(HttpServletRequest request) {
		return request.getHeader(HEADER) != null &&
			   request.getHeader(HEADER).startsWith(PREFIX);
	}

	private Claims validateToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		return  (Claims) Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build().parse(jwtToken).getBody();
//		return  Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
	}

	/**
	 * Metodo para autenticarnos dentro del flujo de Spring
	 * @param claims
	 */
	private void setUpSpringAuthentication(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List<String>) claims.get("authorities");
		
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
				claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
		
	//For Problems with @VALUE and testing using Property
	private String getProperty(String property) {
		String value="";
		try {
			Properties properties = new Properties();
			properties.load(getClass().getResourceAsStream("/application.properties"));
			value = properties.getProperty(property);		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}
	
}
