package com.curso.controlador;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curso.dto.LoginDTO;
import com.curso.dto.RegistroDTO;
import com.curso.entidades.Rol;
import com.curso.entidades.Usuario;
import com.curso.repositorio.IRolRepositorio;
import com.curso.repositorio.IUsuarioRepositorio;

@RestController
@RequestMapping("/api/auth")
public class AuthControlador {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private IUsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private IRolRepositorio rolRepositorio;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/iniciarSesion")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseEntity<>("Ha iniciado sesión con éxito", HttpStatus.OK);
	}
	
	@PostMapping("/registrar")
	public ResponseEntity<?> registrarUsuario(@RequestBody RegistroDTO registroDTO){
		//Comprobamos si existe el nombre de usuario o el email
		if(usuarioRepositorio.existsByUsername(registroDTO.getUsername()))
			return new ResponseEntity<>("Ese nombre de usuario ya existe", HttpStatus.BAD_REQUEST);
		
		if(usuarioRepositorio.existsByEmail(registroDTO.getEmail()))
			return new ResponseEntity<>("Ese email de usuario ya existe", HttpStatus.BAD_REQUEST);
		
		//Si ha llegado aqui, es porque no existe. Lo creamos
		Usuario usuario = new Usuario();
		usuario.setNombre(registroDTO.getNombre());
		usuario.setUsername(registroDTO.getUsername());
		usuario.setEmail(registroDTO.getEmail());
		usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
		
		//Asignamos los roles
		Rol rolesRol = rolRepositorio.findByNombre("ROLE_ADMIN").get();
		usuario.setRoleSet(Collections.singleton(rolesRol));
		
		//Guardamos el usuario
		usuarioRepositorio.save(usuario);
		
		return new ResponseEntity<>("Usuario registrado existosamente", HttpStatus.OK);
	}
	
}
