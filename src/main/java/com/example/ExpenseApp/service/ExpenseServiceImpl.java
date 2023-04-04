package com.example.ExpenseApp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ExpenseApp.Exception.ResourceNotFound;
import com.example.ExpenseApp.entity.Expense;
import com.example.ExpenseApp.repository.ExpenseRepository;

import jakarta.validation.Valid;

@Service
public class ExpenseServiceImpl implements ExpenseService{
	
	@Autowired
	ExpenseRepository repo;
	
	@Autowired
	UserService userService;

	@Override
	public Expense saveToDb(Expense expense) {
		expense.setUser(userService.getLoggedInUserDetails());  //To set the current loggedin usedDetails 
		return repo.save(expense);
	}
	
	@Override
	public List<Expense> readFromDb() {
		return repo.findAll();
	}

	@Override
	public Expense readIdFromDb(int id) {
		Optional<Expense> exp = repo.findById(id);
		if (exp.isPresent()) {
			return exp.get();
		} else
			throw new ResourceNotFound(String.format("Expense not found for %s", id));
	}

	@Override
	public void deleteIdFromDb(int id) {
		Optional<Expense> exp = repo.findById(id);
		if (exp.isEmpty()) {
			throw new ResourceNotFound(String.format("Expense not found for %s", id));
		} else {
			repo.deleteById(id);
		}
	}

	@Override
	public Expense updateDb(Expense expense, int id) {
		Expense existingData = repo.findById(id).get();
		existingData.setExpenseName(expense.getExpenseName());
		existingData.setExpenseAmount(expense.getExpenseAmount());
		
		return repo.save(existingData);
	}

	@Override
	public List<Expense> filterByExpenseName(String expenseName) {
		return repo.findByExpenseName(expenseName);
	}

	public List<Expense> filterByExpenseAmount(int expenseAmount) {
		return repo.findByExpenseAmount(expenseAmount);
	}
}
