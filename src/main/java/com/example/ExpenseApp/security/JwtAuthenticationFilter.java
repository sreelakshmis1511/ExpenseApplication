package com.example.ExpenseApp.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.ExpenseApp.Exception.JwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
//To Authenticate the JWT Token from the request & is executed Once per each request
public class JwtAuthenticationFilter extends OncePerRequestFilter {    

	
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwtToken = getJwtTokenFromRequest(request);

		
		//Validate JwtToken
		try {
			if(StringUtils.hasText(jwtToken) &&  jwtTokenProvider.validateJwtToken(jwtToken)) {
			
				//Get username/email from the token
				String emailid = jwtTokenProvider.getEmailid(jwtToken);
				
				
				// load the user details, password from db, associated with the jwt Token
				UserDetails userdetails = customUserDetailsService.loadUserByUsername(emailid);
				
				//Pass the obtained userDetails to object of Username PasswordAuthenticationToken class
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userdetails,
						null, userdetails.getAuthorities());
			
				// passing the request to authentication token
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} catch (UsernameNotFoundException e) {
			 new JwtException("UsernameNotFoundException");
		} catch (JwtException e) {
			 new JwtException("JwtException");
		}
		
		filterChain.doFilter(request, response);  //Continue with chain of filters
	}

	
	
	// Get JWT Token from the Http request (token is in request -> authorization - (Bearer + token)
	private String getJwtTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			String jwtToken = bearerToken.substring(7);
			return jwtToken;
		}
		return null;
	}
}
