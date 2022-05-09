package com.curso.servicio;

import java.util.List;

import com.curso.dto.ComentarioDTO;

public interface IComentarioServicio {
	
	public ComentarioDTO creaComentario(long publicacionId, ComentarioDTO comentarioDTO);
	
	public List<ComentarioDTO> obtenerComentariosPorPublicacionId(long publicacionId);
	
	public ComentarioDTO obtenerComentarioPorId(Long publicacionId,Long comentarioId);
	
	public ComentarioDTO actualizarComentario(Long publicacionId,Long comentarioId,ComentarioDTO solicitudDeComentario);
	
	public void eliminarComentario(Long publicacionId,Long comentarioId);
	
}
