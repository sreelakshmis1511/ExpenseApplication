package com.example.ExpenseApp.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ExpenseApp.Dto.UserDto;
import com.example.ExpenseApp.Exception.EmailAlreadyExistsException;
import com.example.ExpenseApp.Exception.ResourceNotFound;
import com.example.ExpenseApp.entity.User;
import com.example.ExpenseApp.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
@Autowired
PasswordEncoder passwordencoder;
	
	@Autowired
	UserRepository repo;
	
	ModelMapper mapper = new ModelMapper();
	
	
	
	//Save to Db if emailId doesnt exists already
	public UserDto doregistration(UserDto userDto) {
		User user = mapper.map(userDto, User.class);
		if(repo.existsByemailid(userDto.getEmailid())) {
			throw new EmailAlreadyExistsException(userDto.getEmailid());
		}
		user.setPassword(passwordencoder.encode(user.getPassword()));  //To encode the pw before storing to DB
		User savedUser = repo.save(user);
		
		return mapper.map(savedUser, UserDto.class);
		
	}


	public UserDto readRegistration(int id) {
		return mapper.map(repo.findById(id).get(), UserDto.class);
	}


	public UserDto updateRegistration(int id, UserDto userDto) {
		Optional<User> user = repo.findById(id);
		if (user.isEmpty()) {
			throw new ResourceNotFound(String.format("%s not found", id));
		}
		user.get().setAge(userDto.getAge());
		user.get().setEmailid(userDto.getEmailid());
		user.get().setName(userDto.getName());
		user.get().setPassword(passwordencoder.encode(userDto.getPassword()));
		
		return mapper.map(user, UserDto.class);
	}


	public void deleteRegistration(int id) {
		 repo.deleteById(id);
	}


	@Override
	public User getLoggedInUserDetails() {
		
	Authentication authentication =	SecurityContextHolder.getContext().getAuthentication();
	String emailid = authentication.getName(); //we saved email nd password
	
	User loggedUser = repo.findByemailid(emailid).orElseThrow(() -> new UsernameNotFoundException(String.format("%s doesn't exist already", emailid)));
	return loggedUser;
	
	}


	
	
}
