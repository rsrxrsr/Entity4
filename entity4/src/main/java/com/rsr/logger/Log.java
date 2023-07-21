package com.rsr.logger;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Log {
	@Id
	private Timestamp fechaHr;
	private String metodo;
	private String uri;
	private String usuario;
	private Integer estatus;
	@Column(length=1024)	
	private String body;
//
	public Log() {
		
	}
	
	public Log(
		Timestamp fechaHr,
		String metodo,
		String uri,
		String usuario,
		Integer estatus)
	{
		setFechaHr(fechaHr);
		setMetodo(metodo);
		setUri(uri);
		setUsuario(usuario);
		setEstatus(estatus);				
	}
//	
}
	