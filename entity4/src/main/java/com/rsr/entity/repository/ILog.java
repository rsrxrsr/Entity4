package com.rsr.entity.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.rsr.entity.model.Log;

@RepositoryRestResource
public interface ILog extends JpaRepository<Log, Timestamp> {
	public abstract Log findByUsuario(String usuario);
}
