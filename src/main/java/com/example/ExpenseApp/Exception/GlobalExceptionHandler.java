package com.example.ExpenseApp.Exception;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
//	@ExceptionHandler(EmailExistsException.class)
//	public ResponseEntity<errorDetails> emailExists(EmailExistsException email, WebRequest web){
//		errorDetails obj = new errorDetails(LocalDateTime.now(), email.getMessage(), web.getDescription(false));
//		
//		return new ResponseEntity<>(obj, HttpStatus.CONFLICT);
//	}
	
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ErrorDetails> emailAlreadyExistsException(EmailAlreadyExistsException email, WebRequest web){
		ErrorDetails error = new ErrorDetails(email.getMessage(), web.getDescription(false), LocalDate.now());
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(value = ResourceNotFound.class)
	public ResponseEntity<ErrorDetails> handleExpenseNotFoundException(ResourceNotFound notFound, WebRequest web) {
		ErrorDetails error = new ErrorDetails(notFound.getMessage(), web.getDescription(false), LocalDate.now());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Map<String, String> errors = new HashMap<>();

		List<ObjectError> errorList = ex.getBindingResult().getAllErrors();

		errorList.stream().forEach(a -> {
			String fieldName = ((FieldError) a).getField();
			String message = ((FieldError) a).getDefaultMessage();
			errors.put(fieldName, message);
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorDetails> handlerMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException exc, WebRequest web) {
		ErrorDetails error = new ErrorDetails(exc.getMessage(), web.getDescription(false), LocalDate.now());
		return new ResponseEntity<ErrorDetails>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ErrorDetails> generalizedException(Exception exc, WebRequest web) {
		ErrorDetails error = new ErrorDetails(exc.getMessage(), web.getDescription(false), LocalDate.now());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

}
