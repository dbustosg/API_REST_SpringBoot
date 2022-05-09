package com.curso.excepciones;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.curso.dto.ErrorDetalles;

//Se le agrega esta notacion, para decirle a la clase que va a poder capturar todas las excepciones handler
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetalles> manejarResourseNotFoundException(
			ResourceNotFoundException exception,
			WebRequest webRequest){
		
		ErrorDetalles errorDetalles = new ErrorDetalles(
				new Date(),
				exception.getMessage(),
				webRequest.getDescription(false));
		
		return new ResponseEntity<>(errorDetalles, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BlogAppException.class)
	public ResponseEntity<ErrorDetalles> manejarBlogAppException(
			ResourceNotFoundException exception,
			WebRequest webRequest){
		
		ErrorDetalles errorDetalles = new ErrorDetalles(
				new Date(),
				exception.getMessage(),
				webRequest.getDescription(false));
		
		return new ResponseEntity<>(errorDetalles, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetalles> manejarGlobalException(
			ResourceNotFoundException exception,
			WebRequest webRequest){
		
		ErrorDetalles errorDetalles = new ErrorDetalles(
				new Date(),
				exception.getMessage(),
				webRequest.getDescription(false));
		
		return new ResponseEntity<>(errorDetalles, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
