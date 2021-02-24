package com.patrickontheweb.fit.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

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
		Map<String, String> errors = new HashMap<>();
		DataIntegrityViolationException e = DataIntegrityViolationException.class.cast(exception);
		org.hibernate.exception.ConstraintViolationException ex = org.hibernate.exception.ConstraintViolationException.class.cast(e.getCause());
		errors.put("constraint", ex.getConstraintName());
		errors.put("sql1", String.valueOf(ex.getSQLException()));
		errors.put("sql2", String.valueOf(ex.getSQLException().getErrorCode()));
		errors.put("sql3", String.valueOf(ex.getSQLException().getCause()));
		errors.put("sql4", String.valueOf(ex.getSQLException().getSQLState()));
		errors.put("sql5", String.valueOf(ex.getSQL()));
		errors.put("message", String.valueOf(ex.getMessage()));
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleGeneralException(RuntimeException exception, WebRequest request) {
		logException(exception, request);
		return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = { ConstraintViolationException.class })
	public ResponseEntity<Object> handleValidationException(RuntimeException exception, WebRequest request) {
		logException(exception, request);
		List<Map<String, String>> errors = new ArrayList<>();
		if(exception instanceof ConstraintViolationException) {
			ConstraintViolationException e = ConstraintViolationException.class.cast(exception);
			//TODO: cleanup to use proper json
			e.getConstraintViolations().stream()
				.forEach(g -> {
					Map<String, String> error = new HashMap<>();
					error.put("field", String.valueOf(g.getPropertyPath()));
					error.put("message", g.getMessage());
					error.put("invalidValue", String.valueOf(g.getInvalidValue()));
					errors.add(error);
				});
		}
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}
	
	private void logException(RuntimeException exception, WebRequest request) {
		logger.info(String.format("Handling exception of type [%s]: %s", exception.getClass(), exception.getMessage()));
	}

}
