package com.example.ExpenseApp.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ExpenseApp.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String emailid) throws UsernameNotFoundException {
		
		com.example.ExpenseApp.entity.User existingUser = repo.findByemailid(emailid).orElseThrow(() -> new UsernameNotFoundException(String.format("%s doesn't exist already", emailid)));
		
		return new User(existingUser.getEmailid(), existingUser.getPassword(), new ArrayList<>());
	}

	
	

}
