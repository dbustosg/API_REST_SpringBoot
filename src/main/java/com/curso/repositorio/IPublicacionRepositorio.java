package com.curso.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.curso.entidades.*;

public interface IPublicacionRepositorio extends JpaRepository<Publicacion, Long>{

}
