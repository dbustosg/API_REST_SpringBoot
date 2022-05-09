package com.curso.controlador;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curso.dto.ComentarioDTO;
import com.curso.servicio.IComentarioServicio;

@RestController
@RequestMapping("/api/")
public class ComentarioControlador {

	@Autowired
	private IComentarioServicio comentarioServicio;
	
	@GetMapping("/publicaciones/{publicacionId}/comentarios")
	public List<ComentarioDTO> listarComentariosPorPublicacion(
			@PathVariable(value = "publicacionId") Long publicacionId){
		return comentarioServicio.obtenerComentariosPorPublicacionId(publicacionId);
	}
	
	@GetMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
	public ResponseEntity<ComentarioDTO> obtenerComentarioPorId(
			@PathVariable(value = "publicacionId") long publicacionId,
			@PathVariable(value = "comentarioId") long comentarioId){
		ComentarioDTO comentarioDTO = comentarioServicio.obtenerComentarioPorId(publicacionId, comentarioId);
		
		return new ResponseEntity<>(comentarioDTO, HttpStatus.OK);
	}
	
	@PostMapping("/publicaciones/{publicacionId}/comentarios")
	public ResponseEntity<ComentarioDTO> guardarComentario(
			@PathVariable(value = "publicacionId") long publicacionId,
			@Valid @RequestBody ComentarioDTO comentarioDTO) {
		return new ResponseEntity<>(comentarioServicio.creaComentario(publicacionId, comentarioDTO),
				HttpStatus.CREATED);
	}
	
	@PutMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
	public ResponseEntity<ComentarioDTO> actualizarComentario(
			@PathVariable(value = "publicacionId") long publicacionId,
			@PathVariable(value = "comentarioId") long comentarioId,
			@Valid @RequestBody ComentarioDTO comentarioDTO){
		
		ComentarioDTO comentarioActualizado = comentarioServicio.actualizarComentario(publicacionId, comentarioId, comentarioDTO);
		
		return new ResponseEntity<>(comentarioActualizado, HttpStatus.OK);
	}
	
	@DeleteMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
	public ResponseEntity<String> eliminarComentario(
			@PathVariable(value = "publicacionId") long publicacionId,
			@PathVariable(value = "comentarioId") long comentarioId){
		
		comentarioServicio.eliminarComentario(publicacionId, comentarioId);
		
		return new ResponseEntity<>("Comentario eliminado", HttpStatus.OK);
	}
}
