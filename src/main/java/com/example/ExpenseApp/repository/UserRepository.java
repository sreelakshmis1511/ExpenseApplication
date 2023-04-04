package com.example.ExpenseApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ExpenseApp.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public Boolean existsByemailid(String emailid);
	
	 Optional<User> findByemailid(String emailid);
}
