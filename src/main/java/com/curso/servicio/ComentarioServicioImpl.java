package com.curso.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.curso.dto.ComentarioDTO;
import com.curso.entidades.Comentario;
import com.curso.entidades.Publicacion;
import com.curso.excepciones.BlogAppException;
import com.curso.excepciones.ResourceNotFoundException;
import com.curso.repositorio.IComentarioRepositorio;
import com.curso.repositorio.IPublicacionRepositorio;

@Service
public class ComentarioServicioImpl implements IComentarioServicio {

	//Para poder mapear
	@Autowired
	private ModelMapper modelMapper;
		
	// Inyectamos los repositorios
	@Autowired
	private IComentarioRepositorio comentarioRepositorio;

	@Autowired
	private IPublicacionRepositorio publicacionRepositorio;

	@Override
	public ComentarioDTO creaComentario(long publicacionId, ComentarioDTO comentarioDTO) {
		Comentario comentario = mapearEntidad(comentarioDTO);

		Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

		comentario.setPublicacion(publicacion);

		Comentario nuevoComentario = comentarioRepositorio.save(comentario);

		return mapearDto(nuevoComentario);
	}

	@Override
	public List<ComentarioDTO> obtenerComentariosPorPublicacionId(long publicacionId) {
		List<Comentario> comentarios = comentarioRepositorio.findByPublicacionId(publicacionId);
		return comentarios.stream().map(comentario -> mapearDto(comentario)).collect(Collectors.toList());
	}

	@Override
	public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId) {
		// Buscamos la publicacion
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

		// Buscamos el comentario
		Comentario comentario = comentarioRepositorio.findById(comentarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

		if (!comentario.getPublicacion().getId().equals(publicacion.getId()))
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "Comentario no pertenece a la publicación");

		return mapearDto(comentario);
	}

	@Override
	public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId ,ComentarioDTO solicitudDeComentario) {
		// Buscamos la publicacion
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

		// Buscamos el comentario
		Comentario comentario = comentarioRepositorio.findById(comentarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

		if (!comentario.getPublicacion().getId().equals(publicacion.getId()))
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "Comentario no pertenece a la publicación");
		
		comentario.setNombre(solicitudDeComentario.getNombre());
		comentario.setEmail(solicitudDeComentario.getEmail());
		comentario.setCuerpo(solicitudDeComentario.getCuerpo());
		
		Comentario comentarioActualizado = comentarioRepositorio.save(comentario);
		
		return mapearDto(comentarioActualizado);
	}

	@Override
	public void eliminarComentario(Long publicacionId, Long comentarioId) {
		// Buscamos la publicacion
		Publicacion publicacion = publicacionRepositorio.findById(publicacionId)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

		// Buscamos el comentario
		Comentario comentario = comentarioRepositorio.findById(comentarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

		if (!comentario.getPublicacion().getId().equals(publicacion.getId()))
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "Comentario no pertenece a la publicación");

		comentarioRepositorio.delete(comentario);
	}

	// Mapeamos de entidad a DTO
	private ComentarioDTO mapearDto(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioDTO.class);
	}

	// Mapeamos de DTO a entidad
	private Comentario mapearEntidad(ComentarioDTO comentarioDTO) {
		return modelMapper.map(comentarioDTO, Comentario.class);
	}
}
