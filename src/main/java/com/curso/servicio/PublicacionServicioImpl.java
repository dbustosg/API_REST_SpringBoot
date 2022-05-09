package com.curso.servicio;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.curso.dto.PublicacionDTO;
import com.curso.dto.PublicacionRespuesta;
import com.curso.entidades.Publicacion;
import com.curso.excepciones.ResourceNotFoundException;
import com.curso.repositorio.IPublicacionRepositorio;

@Service
public class PublicacionServicioImpl implements IPublicacionServicio {

	// Para poder mapear
	@Autowired
	private ModelMapper modelMapper;

	@Autowired // Inyectamos
	private IPublicacionRepositorio publicacionRepositorio;

	@Override
	public PublicacionDTO crearPublicacionDTO(PublicacionDTO publicacionDTO) {
		// Convertimos DTO a Entidad para poder persistirla más tarde
		Publicacion publicacion = mapearEntidad(publicacionDTO);

		// Almacenamos la publicacion en la base de datos
		Publicacion nuevaPublicacion = publicacionRepositorio.save(publicacion);

		// Convertimos de entidad a DTO
		PublicacionDTO publicacionRespuestaDto = mapearDto(nuevaPublicacion);

		return publicacionRespuestaDto;
	}

	// Mostrará todos los registros de publicaciones
	@Override
	public PublicacionRespuesta obtenerPublicaciones(int numeroDePagina, int medidaDePagina, String ordenarPor,
			String sortDir) {
		// Condicional dirección de ordenación
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor).ascending()
				: Sort.by(ordenarPor).descending();

		// Lista los elementos de una cierta pagina y un número máximo de elementos,
		// ordenado
		Pageable pegeable = PageRequest.of(numeroDePagina, medidaDePagina, sort);

		Page<Publicacion> publicaciones = publicacionRepositorio.findAll(pegeable);

		List<Publicacion> listaDePublicaciones = publicaciones.getContent();

		// La lista que obtenemos la metemos en un flujo stream y mapeamos los datos de
		// la lista convirtiendo a DTO
		List<PublicacionDTO> contenido = listaDePublicaciones.stream().map(publicacion -> mapearDto(publicacion))
				.collect(Collectors.toList());

		PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
		publicacionRespuesta.setContenido(contenido);
		publicacionRespuesta.setNumeroPagina(publicaciones.getNumber());
		publicacionRespuesta.setMedidaPagina(publicaciones.getSize());
		publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
		publicacionRespuesta.setTotalPaginas(publicaciones.getTotalPages());
		publicacionRespuesta.setUltima(publicaciones.isLast());

		return publicacionRespuesta;
	}

	@Override
	public PublicacionDTO obtenerPublicacionPorId(Long id) {
		// Buscamos la publicacion por su ID y en el caso que no lo encuentre lanzamos
		// la excepcion creada
		Publicacion publicacion = publicacionRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));

		// Mapeamos la publicacion a DTO
		return mapearDto(publicacion);
	}

	@Override
	public PublicacionDTO actualizaPublicacion(PublicacionDTO publicacionDTO, long id) {
		// Buscamos la publicacion por su ID y en el caso que no lo encuentre lanzamos
		// la excepcion creada
		Publicacion publicacion = publicacionRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));

		publicacion.setDescripcion(publicacionDTO.getDescripcion());
		publicacion.setTitulo(publicacionDTO.getTitulo());
		publicacion.setContenido(publicacionDTO.getContenido());

		Publicacion publicacionActualizada = publicacionRepositorio.save(publicacion);

		return mapearDto(publicacionActualizada);
	}

	@Override
	public void eliminarPublicacion(long id) {
		// Buscamos la publicacion por su ID y en el caso que no lo encuentre lanzamos
		// la excepcion creada
		Publicacion publicacion = publicacionRepositorio.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));

		// Eliminamos la publicacion
		publicacionRepositorio.delete(publicacion);
	}

	// Este metodo convierte entidad a DTO
	private PublicacionDTO mapearDto(Publicacion publicacion) {
		return modelMapper.map(publicacion, PublicacionDTO.class);
	}

	// Este metodo convierte DTO a entidad
	private Publicacion mapearEntidad(PublicacionDTO publicacionDTO) {
		return modelMapper.map(publicacionDTO, Publicacion.class);
	}

}
