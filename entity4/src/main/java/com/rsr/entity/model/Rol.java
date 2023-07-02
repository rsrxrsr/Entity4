package com.rsr.entity.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Rol {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String rol;

	@JsonIgnore
	@ManyToMany(mappedBy="roles", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Usuario> usuarios;

	@ManyToMany
	private List<Funcion> funciones;
	
}
