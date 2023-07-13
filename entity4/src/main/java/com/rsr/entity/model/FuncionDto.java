package com.rsr.entity.model;

import java.util.List;
import java.util.stream.Collectors;

public class FuncionDto extends Funcion {

	private String permisos;
	
	public FuncionDto(Funcion funcion) {
		setFuncion(funcion.getFuncion());
		setMetodo(funcion.getMetodo());
		setUrl(funcion.getUrl());
		setPermisos(funcion.getRoles());
	}

	public void setPermisos(List<Rol> roles) {
		permisos =  roles.stream().map(Rol::getRol).collect(Collectors.joining(","));
	}

	public String getPermisos() {
		return permisos;
	}

}	
