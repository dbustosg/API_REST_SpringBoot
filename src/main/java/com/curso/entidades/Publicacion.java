package com.curso.entidades;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "publicaciones", uniqueConstraints = { @UniqueConstraint(columnNames = { "titulo" }) })
public class Publicacion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "titulo", nullable = false)
	private String titulo;
	
	@Column(name = "descripcion", nullable = false)
	private String descripcion;
	
	@Column(name = "contenido", nullable = false)
	private String contenido;
	
	@JsonBackReference
	@OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true,fetch=FetchType.EAGER)
	/**
	 * Aquí me daba un error: org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.curso.entidades.Publicacion.comentarios, could not initialize proxy - no Session
	 * Lo he solucionado añadiendo el fetch: EAGER, este hace que comentario se instancie antes,
	 * para que de esta manera exista al intentar obtenerlo
	 */
	private Set<Comentario> comentarios = new HashSet<>();

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

	public Publicacion() {
		super();
	}

	public Publicacion(Long id, String titulo, String descripcion, String contenido) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.contenido = contenido;
	}

}
