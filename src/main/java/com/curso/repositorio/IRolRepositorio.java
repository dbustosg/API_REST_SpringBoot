package com.curso.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso.entidades.Rol;

public interface IRolRepositorio extends JpaRepository<Rol, Long>{

	public Optional<Rol> findByNombre(String nombre);
	
}
