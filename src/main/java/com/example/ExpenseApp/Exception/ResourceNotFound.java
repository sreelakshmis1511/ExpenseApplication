package com.example.ExpenseApp.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFound extends RuntimeException{

	public ResourceNotFound(String message) {
		//super(String.format("%s Not found in expense table!", id));
		super(message);
	}
}
