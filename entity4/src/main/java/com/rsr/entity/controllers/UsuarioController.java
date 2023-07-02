package com.rsr.entity.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.rsr.entity.model.Usuario;
import com.rsr.entity.model.UsuarioDto;
import com.rsr.entity.repository.IUsuario;
import com.rsr.entity.services.UsuarioService;

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
	Usuario findByUsuario(@RequestParam("usuario") String usuario) {
		return usuarioRepository.findByUsuario(usuario);
	}

	@GetMapping(path="/findByUsuarioOrPassword")
	List<Usuario> findByUsuarioOrPassword(@RequestParam("valor") String valor) {
		return usuarioRepository.findByUsuarioOrPassword(valor, valor);
	}

	@GetMapping(path="/queryByEstatus")
	List<Usuario> queryByestatus(@RequestParam("estatus") Integer estatus) {
		return usuarioRepository.queryByEstatus(estatus);
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
	Usuario login(@RequestParam("usuario") String usuario, @RequestParam("password") String password) {
		return new UsuarioDto(usuarioService.login(new UsuarioDto(usuario, password)));
	}

	@PostMapping(path="/login")
	UsuarioDto login(@RequestBody Usuario usuario) {
		return new UsuarioDto(usuarioService.login(usuario));
	}
	
	@PostMapping()
    Usuario save(@RequestBody Usuario usuario) {    	
    	return usuarioService.save(usuario);
    }
    
    @DeleteMapping(path="/{id}")
    Boolean deleteById(@PathVariable("id") Long id) {
    	return usuarioService.deleteById(id);
    }
        
}
