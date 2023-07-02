package com.rsr.entity.model;

//import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AreaDto extends Area {

	@SuppressWarnings("unused")
	private List<AreaDto> areas;
	@SuppressWarnings("unused")
	private List<Usuario> usuarios;

	public AreaDto (Area area) {
    	this.setId(area.getId());
    	this.setArea(area.getArea());
    	//this.setJefatura(area.getJefatura());
    	this.setAreas(sinJefatura(area.getAreas()));    	
    	this.setUsuarios(area.getUsuarios());    	
	}
	
	List<Area> sinJefatura(List<Area> areas) {
/*
		List<Area> areasDto = new ArrayList<Area>();
		areas.forEach(area -> {areasDto.add(new AreaDto(area));});
		return areasDto;
*/
		return areas.stream().map(AreaDto::new).collect(Collectors.toList());
	}

}
