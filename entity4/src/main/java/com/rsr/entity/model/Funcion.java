package com.rsr.entity.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Funcion {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String funcion;
	private String url;
	private String metodo;
	
	@JsonIgnore
	@ManyToMany(mappedBy="funciones", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Rol> roles;

}
