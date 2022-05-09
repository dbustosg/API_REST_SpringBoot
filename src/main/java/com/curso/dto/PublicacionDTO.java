package com.curso.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.curso.entidades.Comentario;

//Un DTO es un objeto cuya finalidad es transportar datos entre procesos (almacena y entrega sus propios datos)
public class PublicacionDTO {

	private Long id;
	
	@NotEmpty
	@Size(min = 2, message = "El titulo de la publicación debería tener al menos 2 caracteres")
	private String titulo;
	
	@NotEmpty
	@Size(min = 10, message = "La descripción de la publicación debería tener al menos 10 caracteres")
	private String descripcion;
	
	@NotEmpty
	private String contenido;
	
	private Set<Comentario> comentarios;
	
	public Set<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public PublicacionDTO() {
		super();
	}

}
