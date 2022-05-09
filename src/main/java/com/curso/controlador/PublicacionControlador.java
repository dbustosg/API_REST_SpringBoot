package com.curso.controlador;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.curso.dto.PublicacionDTO;
import com.curso.dto.PublicacionRespuesta;
import com.curso.servicio.IPublicacionServicio;
import com.curso.utilidades.AppConstantes;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionControlador {
	
	@Autowired //Inyectamos nuestro servicio
	private IPublicacionServicio publicacionServicio;
	
	@GetMapping
	public PublicacionRespuesta listarPublicacionDTOs( 
			//Variables para la paginación, para listar las publicaciones con un orden de paginas
			@RequestParam(value = "pageNo", defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO, required = false) int numeroDePagina,
			@RequestParam(value = "pageSize", defaultValue = AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaPagina,
			@RequestParam(value = "sortBy", defaultValue = AppConstantes.ORDENAR_POR_DEFECTO, required = false) String ordenarPor,
			@RequestParam(value = "sortDir", defaultValue = AppConstantes.DIRECCION_ORDENAR_POR_DEFECTO, required = false) String sortDir) {
		return publicacionServicio.obtenerPublicaciones(numeroDePagina,medidaPagina,ordenarPor,sortDir);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable(name = "id") long id){
		return ResponseEntity.ok(publicacionServicio.obtenerPublicacionPorId(id));
	}
	
	@PostMapping
	public ResponseEntity<PublicacionDTO> guardarPublicacion(@RequestBody PublicacionDTO publicacionDTO){
		return new ResponseEntity<>(publicacionServicio.crearPublicacionDTO(publicacionDTO),HttpStatus.CREATED);
	} 
	
	@PutMapping("/{id}")
	public ResponseEntity<PublicacionDTO> actualizarPublicacion
			(@RequestBody PublicacionDTO publicacionDTO,
			@PathVariable(name = "id") long id){
		PublicacionDTO publicacionRespuesta = publicacionServicio.actualizaPublicacion(publicacionDTO, id);
		return new ResponseEntity<PublicacionDTO>(publicacionRespuesta, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> eliminarPublicacion(@PathVariable(name = "id") long id){
		publicacionServicio.eliminarPublicacion(id);
		
		return new ResponseEntity<>("Publicación eliminada con éxito", HttpStatus.OK);
	}
}
