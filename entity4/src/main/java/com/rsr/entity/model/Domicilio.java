package com.rsr.entity.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Domicilio {
	String calle;
	String numero;
	String interior;
	String localidad;
	String municipio;
	String estado;
	String cp;
	String latitud;
	String longitud;

}
