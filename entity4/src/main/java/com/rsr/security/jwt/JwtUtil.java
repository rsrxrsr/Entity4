package com.rsr.security.jwt;

import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.rsr.entity.model.UsuarioDto;

public class JwtUtil {

	//@Value("${jwt.secret}")
	//private String SECRET;
	private final String SECRET = getProperty("jwt.secret");

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
	
	public Key getSecretKey() {
		Key secretKey = new SecretKeySpec(
				Base64.getDecoder().decode(SECRET),
				SignatureAlgorithm.HS512.getJcaName());
		return secretKey;		
	}
		
	public String getToken(UsuarioDto user) {
		System.out.println("*** getToken Permisos= "+user.getPermisos());		
								
		String token = Jwts.builder()
				.setId("entityJWT")
				.setSubject(user.getUsuario())
				.claim("authorities", user.getAuthorities())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 10*60*1000))
				.signWith(getSecretKey(), SignatureAlgorithm.HS512)
				.compact();

		return token;				
	}
	
}
