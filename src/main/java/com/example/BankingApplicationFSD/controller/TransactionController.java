package com.example.BankingApplicationFSD.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.BankingApplicationFSD.entity.Transaction;
import com.example.BankingApplicationFSD.service.TransactionService;

@RestController
public class TransactionController {
	
	private final TransactionService transactionService;
	
	public TransactionController(TransactionService transactionService) {
		this.transactionService=transactionService;
	}
	
	@PostMapping("/transactions")
	public Transaction createTransaction(@RequestBody Transaction transaction) {
		return transactionService.saveTransaction(transaction);
	}
	
	@GetMapping("/transactions")
	public List<Transaction> getAllTransactions(){
		return transactionService.getAllTransactions();
	}
	
	@GetMapping("/transactions/{id}")
	public Transaction getTransactionById(@PathVariable int id) {
		return transactionService.getTransactionById(id);
	}
	
	@PutMapping("/transactions/{id}")
	public Transaction updateTransaction(@PathVariable int id, @RequestBody Transaction transaction) {
		return transactionService.updateTransaction(id, transaction);
	}
}
