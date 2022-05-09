package com.curso.dto;

import java.util.Set;

import com.curso.entidades.Comentario;

//Un DTO es un objeto cuya finalidad es transportar datos entre procesos (almacena y entrega sus propios datos)
public class PublicacionDTO {

	private Long id;
	private String titulo;
	private String descripcion;
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
