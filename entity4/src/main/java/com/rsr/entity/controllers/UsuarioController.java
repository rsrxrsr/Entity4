package com.rsr.entity.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.rsr.entity.model.Usuario;
import com.rsr.entity.model.UsuarioDto;
import com.rsr.entity.repository.IUsuario;
import com.rsr.entity.services.UsuarioService;

//@CrossOrigin
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	IUsuario usuarioRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping()
	List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	@GetMapping(path="/{id}")
	Usuario findById(@PathVariable("id") Long id) {
		return usuarioRepository.findById(id) .orElse(new Usuario());
	}

	@GetMapping(path="/findByUsuario")
	Optional<Usuario> findByUsuario(@RequestParam("usuario") String usuario) {
		return usuarioRepository.findByUsuario(usuario);
	}

	@GetMapping(path="/findByUsuarioOrPassword")
	List<Usuario> findByUsuarioOrPassword(@RequestParam("valor") String valor) {
		return usuarioRepository.findByUsuarioOrPassword(valor, valor);
	}

	@GetMapping(path="/hqlByEstatus")
	List<Usuario> hqlByEstatus(@RequestParam("estatus") Integer estatus) {
		return usuarioRepository.hqlByEstatus(estatus);
	}
	
	@GetMapping(path="/sqlByEstatus")
	List<Usuario> sqlByestatus(@RequestParam("estatus") Integer estatus) {
		return usuarioRepository.sqlByEstatus(estatus);
	}

	@GetMapping(path="/spByEstatus")
	@Transactional(readOnly = true)
	List<Usuario> FindByestatus(@RequestParam("estatus") Integer estatus) {
		return usuarioRepository.FindByEstatus(estatus);
	}

	@GetMapping(path="/login")
	UsuarioDto login(@RequestParam("usuario") String usuario, @RequestParam("password") String password) {
		return usuarioService.login(new UsuarioDto(usuario, password));
	}

	@PostMapping(path="/login")
	UsuarioDto login(@RequestBody Usuario usuario) {
		return usuarioService.login(usuario);
	}
	
	@PostMapping()
    Usuario save(@RequestBody Usuario usuario) {    	
    	return usuarioService.save(usuario);
    }

	@PostMapping(path="/create")
    ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {    	
    	return usuarioService.create(usuario);
    }

	@PutMapping()
    ResponseEntity<Usuario> update(@RequestBody Usuario usuario) {    	
    	return usuarioService.update(usuario);
    }
	
    @DeleteMapping(path="/{id}")
    Boolean deleteById(@PathVariable("id") Long id) {
    	return usuarioService.deleteById(id);
    }
        
}
