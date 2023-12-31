package com.rsr.security.jwt;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
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
@WebFilter("/**")
public class JwtFilter  extends OncePerRequestFilter  {
	
	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";

	private JwtUtil jwtUtil = new JwtUtil();

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
					System.out.println("*** setAuthentication: "+claims);
				} 
			} 	//  SecurityContextHolder.clearContext();			
			chain.doFilter(request, response);
			System.out.println("*** End SecurityFilter");
		} catch (UnsupportedJwtException | MalformedJwtException e) {
			System.out.println("*** Exception");
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
		return  (Claims) Jwts.parserBuilder().setSigningKey(jwtUtil.getSecretKey()).build().parse(jwtToken).getBody();
//		return  Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
	}

	/**
	 * Metodo para autenticarnos dentro del flujo de Spring
	 * @param claims
	 */
	private void setUpSpringAuthentication(Claims claims) {
		//
		System.out.println("claims.get="+claims.get("authorities"));

		// List<String> auths = (List<String>) claims.get("authorities", List.class); // ,Class requiredType
		List<String> auths = (List) claims.get("authorities");
		Collection<? extends GrantedAuthority> authorities =
				auths.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
				
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				claims.getSubject(), null, authorities);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		System.out.println("AuthsA="+SecurityContextHolder.getContext().getAuthentication());
	}
			
}
