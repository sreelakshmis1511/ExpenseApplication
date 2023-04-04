package com.example.ExpenseApp.service;

import java.util.List;

import com.example.ExpenseApp.entity.Expense;

public interface ExpenseService {

	Expense saveToDb(Expense expense);

	List<Expense> readFromDb();

	Expense readIdFromDb(int id);

	void deleteIdFromDb(int id);

	Expense updateDb(Expense expense, int id);

	List<Expense> filterByExpenseName(String expenseName);

	List<Expense> filterByExpenseAmount(int expenseAmount);
}
