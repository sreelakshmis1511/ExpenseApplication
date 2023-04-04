package com.example.ExpenseApp.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.ExpenseApp.Exception.JwtException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	@Value("${app-jwt-secret}") // To read the value from application.properties
	private String jwtSecret;
	@Value("${app-jwt-expiration-milliseconds}")
	private long jwtExpiration;

	
// Generate JWT Token
	public String generateToken(Authentication authentication) {
		String emailid = authentication.getName();                          // mailid or firstname
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + jwtExpiration);

		String token = Jwts.builder().setSubject(emailid)                   // userDetails
				                     .setIssuedAt(currentDate)              //Token issued Date
				                     .setExpiration(expireDate)
				                     .signWith(SignatureAlgorithm.HS512, jwtSecret)
				                     .compact();
		return token;
	}

	
// Get Username from jwtToken
	public String getEmailid(String token) {
		Claims claims = Jwts.parser() // Claims contains all the details which we set in generateToken method,// subject, issuedAt, ExpirationAt
			            	.setSigningKey(jwtSecret)
			            	.parseClaimsJws(token)
			            	.getBody();

		String username = claims.getSubject();
		return username;
	}

	
// Validate JWT Token
	public boolean validateJwtToken(String token) throws JwtException {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parse(token);
			return true;

		} catch (MalformedJwtException e) {
			throw new JwtException("Invalid JWT Token");
		} catch (ExpiredJwtException e) {
			throw new JwtException("Expired JWT Token");
		} catch (UnsupportedJwtException e) {
			throw new JwtException("unsupported JWT Token");
		} catch (IllegalArgumentException e) {
			throw new JwtException("jwt string is empty");
		}
	}

}
