package com.example.ExpenseApp.entity;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Component
public class JwtResponse {

	private String accessToken;
	private String tokenType = "Bearer";
	private long validityInSeconds;
}
