package com.rsr.entity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.AuthorityUtils;
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
import com.rsr.security.SecurityKey;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired	
	SecurityKey securityKey;
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
	
	public Usuario save(Usuario usuario) {
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
		return usuarioRepository.save(usuario);
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
        usuarioDto.setPassword(securityKey.getToken(usuarioDto));  			
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
	            true, true, true, true,
	            AuthorityUtils.createAuthorityList(user.getPermisos()));
	}
	
} // End Class
