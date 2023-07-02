package com.rsr.entity.model;

import java.util.List;

//import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Usuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String usuario;
	private String password;
	private Integer estatus;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Persona persona;

	@ManyToOne(cascade=CascadeType.ALL)
	private Area area;
	
	@ManyToMany(cascade=CascadeType.ALL)
	private List<Rol> roles;

}
