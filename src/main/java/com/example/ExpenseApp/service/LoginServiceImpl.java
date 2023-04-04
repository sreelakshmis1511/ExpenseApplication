//package com.example.ExpenseApp.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import com.example.ExpenseApp.Dto.LoginDto;
//import com.example.ExpenseApp.security.JwtTokenProvider;
//
//public class LoginServiceImpl implements LoginService{
//	
//	@Autowired
//	private AuthenticationManager authenticationManager;
//	
//	@Autowired
//	private JwtTokenProvider jwtTokenProvider;
//	
//	public String login(LoginDto loginDto) throws Exception {
//		
//		 Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmailid(), loginDto.getPassword()));
//		
//		 SecurityContextHolder.getContext().setAuthentication(authentication);
//		 
//		//Generate the jwt token
//		final String token = jwtTokenProvider.generateToken(authentication);
//		return token;
//	}
//
//}
