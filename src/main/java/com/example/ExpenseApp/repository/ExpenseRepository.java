package com.example.ExpenseApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ExpenseApp.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Integer>{

	
	List<Expense> findByExpenseName(String expenseName);
	
	List<Expense> findByExpenseAmount(int expenseAmount);
}
