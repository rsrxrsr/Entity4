package com.rsr.entity.services;

import java.util.Base64;

import java.util.Date;
import java.util.List;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import io.jsonwebtoken.*;

import com.rsr.entity.model.Usuario;
import com.rsr.entity.model.UsuarioDto;
import com.rsr.entity.repository.IArea;
import com.rsr.entity.repository.IPersona;
import com.rsr.entity.repository.IRol;
import com.rsr.entity.repository.IUsuario;

@Service
public class UsuarioService implements UserDetailsService {

	@Value("${jwt.key}")
	private String SECRET;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
	IArea areaRepository;
	@Autowired
	IPersona personaRepository;
	@Autowired
	IRol rolRepository;
	@Autowired
	IUsuario usuarioRepository;
	
	public Usuario save(Usuario usuario) {
		/*
		Long idArea = areaRepository.findByArea(usuario.getArea().getArea()).getId();
		if (idArea!=null) {
			usuario.getArea().setId(idArea);
		}
		//
		usuario.setArea(areaRepository.save(usuario.getArea()));								
		usuario.setPersona(personaRepository.save(usuario.getPersona()));
		usuario.setRoles(rolRepository.saveAll(usuario.getRoles()));
		*/
		return usuarioRepository.save(usuario);
	}
	
	public boolean deleteById(Long id) {
		if (usuarioRepository.existsById(id)) {
			usuarioRepository.deleteById(id);
			return true;
		}
		return false;    
	}
	
//  Security section	
	@Override
	public UserDetails loadUserByUsername(String username) {
		System.out.println("*** Sunday *** " + username);
		UsuarioDto user = new UsuarioDto(usuarioRepository.findByUsuario(username)
				.orElseThrow(() -> new UsernameNotFoundException("Could not find the user: " + username)));
		System.out.println("*** Usuario: "+username+" Password: " + user.getPassword()+" Permisos: "+user.getPermisos());
	    return new org.springframework.security.core.userdetails.User(
	    		user.getUsuario(), passwordEncoder.encode(user.getPassword()),
	            true, true, true, true,
	            AuthorityUtils.createAuthorityList(user.getPermisos()));
	}

	public UsuarioDto login(Usuario usuario) {
		UsuarioDto usuarioDto = new UsuarioDto(usuarioRepository.findByUsuarioAndPasswordAndEstatus(usuario.getUsuario(), usuario.getPassword(), 1)
			      .orElseThrow(() -> new UsernameNotFoundException("Could not find the user: " + usuario)));
        usuarioDto.setPassword(getToken(usuarioDto));  			
		return usuarioDto;
	} 

	private String getToken(UsuarioDto user) {
		System.out.println("*** getToken() Permisos: "+user.getPermisos()+" :End");
		
		Key secretKey = new SecretKeySpec(
				Base64.getDecoder().decode(SECRET),
				SignatureAlgorithm.HS512.getJcaName()); 
				
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
				.setExpiration(new Date(System.currentTimeMillis() + 60000))
				.signWith(secretKey, SignatureAlgorithm.HS512)
				.compact();

		return token;				
	}
	
} // End Class
