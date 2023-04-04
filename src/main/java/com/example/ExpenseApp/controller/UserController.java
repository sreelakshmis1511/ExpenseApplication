package com.example.ExpenseApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ExpenseApp.Dto.UserDto;
import com.example.ExpenseApp.entity.Login;
import com.example.ExpenseApp.service.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
public class UserController {
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	AuthenticationManager authManager;
	
	@PostMapping("/register")
	public UserDto registration(@RequestBody @Valid UserDto userDto) {
		return userServiceImpl.doregistration(userDto);
	}
	
	
	@GetMapping("/read/{id}")
	public UserDto getRegistration(@PathVariable int id) {
		return userServiceImpl.readRegistration(id);
	}
	
	
	@PutMapping("/update/{id}")
	public UserDto updateRegistration(@PathVariable int id, @RequestBody @Valid UserDto userDto) {
		return userServiceImpl.updateRegistration(id, userDto);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteRegistration(@PathVariable int id) {
		 userServiceImpl.deleteRegistration(id);
	}

}
