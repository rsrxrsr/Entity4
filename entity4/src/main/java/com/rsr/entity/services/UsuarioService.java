package com.rsr.entity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rsr.entity.model.Usuario;
import com.rsr.entity.model.UsuarioDto;
import com.rsr.entity.repository.IArea;
import com.rsr.entity.repository.IPersona;
import com.rsr.entity.repository.IRol;
import com.rsr.entity.repository.IUsuario;
import com.rsr.security.jwt.JwtUtil;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired	
	JwtUtil jwtUtil;
	@Autowired	
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
	IArea areaRepository;
	@Autowired
	IPersona personaRepository;
	@Autowired
	IRol rolRepository;
	@Autowired
	IUsuario usuarioRepository;
	
		/*
		Long idArea = areaRepository.findByArea(usuario.getArea().getArea()).getId();
		if (idArea!=null) {
			usuario.getArea().setId(idArea);
		}
		//
		usuario.setArea(areaRepository.save(usuario.getArea()));								
		usuario.setPersona(personaRepository.save(usuario.getPersona()));
		usuario.setRoles(rolRepository.saveAll(usuario.getRoles()));
		*/
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	
	public ResponseEntity<Usuario> create(Usuario usuario) {
		if (usuario.getId() != null && usuarioRepository.existsById(usuario.getId()))
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(usuario);
		try {
			return ResponseEntity.ok().body(usuarioRepository.save(usuario));
		} catch(Exception er) {
			System.out.printf("Error en BD usuarioService(57) %s", er);
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(usuario);
		}
	}

	public ResponseEntity<Usuario> update(Usuario usuario) {
		if (usuario.getId() == null || !usuarioRepository.existsById(usuario.getId()))
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(usuario);
		return ResponseEntity.ok().body(usuarioRepository.save(usuario));
	}
	
	public boolean deleteById(Long id) {
		if (usuarioRepository.existsById(id)) {
			usuarioRepository.deleteById(id);
			return true;
		}
		return false;    
	}
	
//  Security section
	
	public UsuarioDto login(Usuario usuario) {
		UsuarioDto usuarioDto = new UsuarioDto(usuarioRepository.findByUsuarioAndPasswordAndEstatus(usuario.getUsuario(), usuario.getPassword(), 1)
			      .orElseThrow(() -> new UsernameNotFoundException("Could not find the user: " + usuario)));
        usuarioDto.setPassword(jwtUtil.getToken(usuarioDto));  			
		return usuarioDto;
	} 

	@Override
	public UserDetails loadUserByUsername(String username) {
		System.out.println("*** Sunday *** " + username);
		UsuarioDto user = new UsuarioDto(usuarioRepository.findByUsuario(username)
				.orElseThrow(() -> new UsernameNotFoundException("Could not find the user: " + username)));
		System.out.println("*** Usuario: "+username+" Password: " + user.getPassword()+" Permisos: "+user.getPermisos());
	    return new User(
	    		user.getUsuario(), passwordEncoder.encode(user.getPassword()),
	            true, true, true, true, user.getGrantedAuthority());
	}
	
} // End Class
