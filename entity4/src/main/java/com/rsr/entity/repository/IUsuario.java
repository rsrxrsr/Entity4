package com.rsr.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.rsr.entity.model.Usuario;

public interface IUsuario extends JpaRepository<Usuario, Long> {
	
	abstract Optional<Usuario> findByUsuario(String usuario);
	abstract Optional<Usuario> findByUsuarioAndPasswordAndEstatus(String usuario, String password, Integer estatus);
	abstract List<Usuario> findByUsuarioOrPassword(String usuario, String password);

	@Query (
			value="SELECT u FROM Usuario u WHERE u.estatus = :estatus"
	)
	List<Usuario> queryByEstatus(@Param("estatus") Integer estatus);

	@Query (
			value="SELECT u.* FROM Usuario u WHERE u.estatus = :estatus",
			nativeQuery = true
	)
	List<Usuario> sqlByEstatus(@Param("estatus") Integer estatus);
	
	@Procedure
	List<Usuario> FindByEstatus(Integer estatus);

}
