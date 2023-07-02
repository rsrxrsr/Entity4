package com.rsr.entity.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rsr.entity.model.AreaDto;
import com.rsr.entity.repository.IArea;

@Service
public class AreaService {

	@Autowired
	IArea areaRepository;

	public List<AreaDto> findAll() {
		/*List<AreaDto> areasDto = new ArrayList<AreaDto>();	    
	      areaRepository.findAll().forEach(area -> areasDto.add(new AreaDto(area)));
		  return areasDto; */
		return areaRepository.findAll().stream().map(AreaDto::new).collect(Collectors.toList());
	}
		
}
