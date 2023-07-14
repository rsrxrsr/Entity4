package com.rsr.security;

import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.crypto.spec.SecretKeySpec;

//import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.rsr.entity.model.UsuarioDto;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class SecurityKey {

	//@Value("${jwt.key}")
	//private String SECRETo;
	private final String SECRET = getProperty("jwt.key");

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
		System.out.println("*** getToken() Permisos: "+user.getPermisos()+" :End");		
				
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(user.getPermisos());

/*      AuthorityUtils.createAuthorityList(user.getPermisos())
//		grantedAuthorities.stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.toList()))
*/		
		String token = Jwts.builder()
				.setId("entityJWT")
				.setSubject(user.getUsuario())
				.claim("authorities", grantedAuthorities)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 10*60*1000))
				.signWith(getSecretKey(), SignatureAlgorithm.HS512)
				.compact();

		return token;				
	}

}
