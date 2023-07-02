package com.rsr.entity.model;

public class AreaJefaturaDto extends Area {

	@SuppressWarnings("unused")
	private Area jefatura;

	public AreaJefaturaDto (Area area) {
    	this.setId(area.getId());
    	this.setArea(area.getArea());
    	this.setJefatura(area.getJefatura());    	
    	this.setAreas(area.getAreas());    	
    	this.setUsuarios(area.getUsuarios());    	
	}

}
