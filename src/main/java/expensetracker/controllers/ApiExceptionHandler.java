package expensetracker.controllers;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import expensetracker.exception.UserNotFoundException;
import expensetracker.models.ErrorResponse;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ApiExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handlerDataIntegrity(DataIntegrityViolationException ex){
		logger.error(ex.getMessage());
		return prepareResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> handlerConstraintViolation(ConstraintViolationException ex){
		logger.error(ex.getMessage());
		return prepareResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handlerIllegalArgumentException(HttpMessageNotReadableException ex){
		logger.error(ex.getStackTrace().toString());
		return prepareResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> handlerUserNotFound(UserNotFoundException ex){
		logger.error(ex.getMessage());
		return prepareResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handlerGeneralException(Exception ex){
		logger.error(ex.getMessage());
		return prepareResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private ResponseEntity<?> prepareResponse(String message, HttpStatus status) {
		ErrorResponse response = new ErrorResponse(message, 0);
		return new ResponseEntity<ErrorResponse>(response, status);
	}
}
