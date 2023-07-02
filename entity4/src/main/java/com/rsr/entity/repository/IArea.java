package com.rsr.entity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.rsr.entity.model.Area;

@RepositoryRestResource
public interface IArea extends JpaRepository<Area, Long> {

	public abstract Optional<Area> findByArea(String Area);

}
