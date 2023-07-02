package com.rsr.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rsr.entity.model.Persona;

public interface IPersona extends JpaRepository<Persona, Long> {}
