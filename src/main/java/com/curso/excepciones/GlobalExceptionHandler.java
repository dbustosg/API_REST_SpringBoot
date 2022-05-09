package com.curso.excepciones;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.curso.dto.ErrorDetalles;

//Se le agrega esta notacion, para decirle a la clase que va a poder capturar todas las excepciones handler
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

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
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> erroresMap = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String nombreCampo = ((FieldError)error).getField();
			String mensaje = error.getDefaultMessage();
			
			erroresMap.put(nombreCampo, mensaje);
		});
		
		return new ResponseEntity<>(erroresMap, HttpStatus.BAD_REQUEST);
	}
	
}
