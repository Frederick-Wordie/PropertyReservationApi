package com.hostfully.reservation.exception;

import static com.hostfully.reservation.constants.MessageConstants.BAD_REQUEST;
import static com.hostfully.reservation.constants.MessageConstants.NOT_FOUND;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
		Map<String, String> map = new HashMap<>();
		map.put("error", ex.getMessage());
		map.put("status", NOT_FOUND);
		return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<Object> handleArgumentNotValidException(MethodArgumentNotValidException ex) {
		Map<String, String> map = new HashMap<>();
		map.put("error", ex.getMessage());
		map.put("status", BAD_REQUEST);
		return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
	}
}
