package com.patrickontheweb.fit.exception;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AppResponseExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(AppResponseExceptionHandler.class);
	
	@ExceptionHandler(value = { NoSuchElementException.class })
	public ResponseEntity<Object> handleNoSuchElementException(RuntimeException exception, WebRequest request) {
		logException(exception, request);
		return ResponseEntity.notFound().build();	
	}
	
	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityException(RuntimeException exception, WebRequest request) {
		logException(exception, request);
		return ResponseEntity.badRequest().build();	
	}
	
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleGeneralException(RuntimeException exception, WebRequest request) {
		logException(exception, request);
		return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private void logException(RuntimeException exception, WebRequest request) {
		logger.info(String.format("Handling exception of type [%s]: %s", exception.getClass(), exception.getMessage()));
	}

}
