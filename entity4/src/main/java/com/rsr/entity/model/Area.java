package com.rsr.entity.model;

import java.util.List;

import com.fasterxml.jackson.annotation.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Area {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String area;

    @ManyToOne
	private Area jefatura;
    
	@JsonIgnore
    @OneToMany(mappedBy="jefatura")
    private List<Area> areas; 

	@JsonIgnore
	//@OneToMany(mappedBy="area", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	@OneToMany(mappedBy="area")
	private List<Usuario> usuarios;
	
}