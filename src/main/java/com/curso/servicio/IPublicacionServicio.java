package com.curso.servicio;

import com.curso.dto.PublicacionDTO;
import com.curso.dto.PublicacionRespuesta;

public interface IPublicacionServicio {
	
	public PublicacionDTO crearPublicacionDTO(PublicacionDTO publicacionDTO);
	
	public PublicacionRespuesta obtenerPublicaciones(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir);
	
	public PublicacionDTO obtenerPublicacionPorId(Long id);
	
	public PublicacionDTO actualizaPublicacion(PublicacionDTO publicacionDTO, long id);
	
	public void eliminarPublicacion(long id);
}
