package com.example.ExpenseApp.Dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
	private int id;
	@NotNull(message = "Enter Name")
	private String name;
    
	@NotNull(message = "Enter emailId")
	@Email(message = "Enter a valid mailId")
	private String emailid;
	
	@Size(min = 8, message = "Password should be atleast 8 characters")
	@NotNull(message = "Enter Password")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	private int age;
	
	private LocalDate createdAt;
	
	private LocalDate updaDateAt;
}
