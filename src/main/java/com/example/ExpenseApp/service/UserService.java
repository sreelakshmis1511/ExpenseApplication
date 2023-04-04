package com.example.ExpenseApp.service;

import com.example.ExpenseApp.Dto.UserDto;
import com.example.ExpenseApp.entity.User;

public interface UserService {

	UserDto doregistration(UserDto userDto) ;
	
	UserDto readRegistration(int id);
	
	UserDto updateRegistration(int id, UserDto userDto);
	
	void deleteRegistration(int id);
	
	User getLoggedInUserDetails();
}
