package com.example.ExpenseApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ExpenseApp.Dto.LoginDto;
import com.example.ExpenseApp.entity.JwtResponse;
import com.example.ExpenseApp.security.CustomUserDetailsService;
import com.example.ExpenseApp.security.JwtTokenProvider;
import com.example.ExpenseApp.service.UserService;

@RestController
public class LoginController {

	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private JwtResponse jwtResponse;
	
	@Value("${app-jwt-expiration-milliseconds}")
	private long jwtExpiration;
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody LoginDto loginDto) throws Exception {
		
		 Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmailid(), loginDto.getPassword()));
		 SecurityContextHolder.getContext().setAuthentication(authentication);
		 
		//Generate the jwt token
		final String token = jwtTokenProvider.generateToken(authentication);
		
		jwtResponse.setAccessToken(token);
		jwtResponse.setValidityInSeconds(jwtExpiration);
		return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.OK);
	}
	
}
