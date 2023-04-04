package com.example.ExpenseApp.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailAlreadyExistsException extends RuntimeException {

	public EmailAlreadyExistsException(String emailId){
	super(String.format("%s Already exists...", emailId));	
	}
}
