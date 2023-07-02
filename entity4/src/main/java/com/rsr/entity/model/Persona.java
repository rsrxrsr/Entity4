package com.rsr.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Persona {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String persona;
	@Enumerated(EnumType.STRING)
	private Genero genero;
    @Embedded
    private Domicilio domicilio;
		
	@JsonIgnore
	@OneToOne(mappedBy="persona")
	private Usuario usuario;
	
}

enum Genero {
	HOMBRE,	MUJER	
}
