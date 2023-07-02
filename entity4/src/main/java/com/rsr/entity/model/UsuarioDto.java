package com.rsr.entity.model;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioDto extends Usuario {
	
	private String permisos;
	
	public UsuarioDto (String usuario, String password) {
		setUsuario(usuario);
		setPassword(password);
	}

	public UsuarioDto (Usuario usuario) {
		setUsuario(usuario.getUsuario());
		setArea(usuario.getArea());
		setPermisos(usuario.getRoles());
	}

	public void setPermisos(List<Rol> roles) {
		/*		
		permisos=roles.get(0).getRol();
		for (int i=1;i<roles.size();i++) permisos += ", " + roles.get(i).getRol();
		*/
		permisos =  roles.stream().map(Rol::getRol).collect(Collectors.joining(","));
	}

	public String getPermisos() {
		return permisos;
	}
	
}
