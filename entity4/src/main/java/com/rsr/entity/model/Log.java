package com.rsr.entity.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Log {
	@Id
	private Timestamp  timestamp;
	private String method;
	private String uri;
	private String usuario;
	private Integer status;
	@Column(length=1024)	
	private String body;
//
	public Log() {
		
	}
	
	public Log(
		Timestamp  timestamp,
		String method,
		String url,
		String usuario,
		Integer status)
	{
		setTimestamp(timestamp);
		setMethod(method);
		setUri(url);
		setUsuario(usuario);
		setStatus(status);				
	}
//	
}
	