package com.rsr.entity.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UsuarioDto extends Usuario {
		
	public UsuarioDto (String usuario, String password) {
		setUsuario(usuario);
		setPassword(password);
	}

	public UsuarioDto (Usuario usuario) {
		setUsuario(usuario.getUsuario());
		setPassword(usuario.getPassword());
		setArea(usuario.getArea());
		setRoles(usuario.getRoles());
	}

	public String getPermisos() {
		return getRoles().stream()
			.map(Rol::getRol).collect(Collectors.joining(","));
	}

	public List<String> getAuthorities() {
		return getRoles().stream()
			.map(Rol::getRol).collect(Collectors.toList());
	}

	public Collection<? extends GrantedAuthority> getGrantedAuthority() {
		return getRoles().stream()
			.map(rol-> new SimpleGrantedAuthority(rol.getRol())).collect(Collectors.toList());
	}
				
	/*	Get Authorities			
	List<GrantedAuthority> grantedAuthorities = 
		AuthorityUtils.commaSeparatedStringToAuthorityList(user.getPermisos());
	Or		
	    AuthorityUtils.createAuthorityList(user.getPermisos()));
	And
	List<String> grantedAuthorities =
		grantedAuthorities.stream()
		.map(GrantedAuthority::getAuthority)
		.collect(Collectors.toList());

	public List<String> getAuthorities() {
		return
			user.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.toList());
	}

	*/
	
	/*		
	permisos=roles.get(0).getRol();
	for (int i=1;i<roles.size();i++) permisos += ", " + roles.get(i).getRol();
	*/
		
}
