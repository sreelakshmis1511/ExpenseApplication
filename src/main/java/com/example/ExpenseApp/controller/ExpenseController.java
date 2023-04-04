package com.example.ExpenseApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.ExpenseApp.entity.Expense;
import com.example.ExpenseApp.service.ExpenseServiceImpl;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/expense")
public class ExpenseController {
	
	
	@Autowired
	ExpenseServiceImpl entityImpl;
	
	@GetMapping("/getAllExpenses")
	public List<Expense>  getAllExpenses(){
		return entityImpl.readFromDb();
	}
	
	@GetMapping("/getAnExpense/{id}")
	public Expense  getAnExpense(@PathVariable int id){
		return entityImpl.readIdFromDb(id);
	}
	
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping("/saveToExpenses")
	public Expense saveToExpenses(@RequestBody Expense expense){
		return entityImpl.saveToDb(expense);
	}
	
	@PutMapping("/updateExpense/{id}")
	public Expense updateExpense(@RequestBody @Valid Expense expense, @PathVariable int id){
		return entityImpl.updateDb(expense, id);
	}
	
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/deleteAnExpense/{id}")
	public void  deleteAnExpense(@PathVariable int id){
		 entityImpl.deleteIdFromDb(id);
	}
	
	//Filtering the records by ExpenseName
	@GetMapping("/filterByName/{expenseName}")
	public List<Expense> filterByName(@PathVariable String expenseName){
	return	entityImpl.filterByExpenseName(expenseName);
	}
	
	//Filtering the records by expenseAmount
	@GetMapping("/filterByExpenseAmount")
	public List<Expense> filterByExpenseAmount(@RequestParam int expenseAmount){
	return	entityImpl.filterByExpenseAmount(expenseAmount);
	}

}
