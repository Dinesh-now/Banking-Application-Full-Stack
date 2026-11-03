package com.example.BankingApplicationFSD.controller;

import java.util.List;
import com.example.BankingApplicationFSD.dto.TransactionResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	public TransactionResponseDTO createTransaction(@RequestBody Transaction transaction) {
		Transaction savedTransaction = transactionService.saveTransaction(transaction);

		return transactionService.convertToDTO(savedTransaction);
	}
	
	@GetMapping("/transactions")
	public List<TransactionResponseDTO> getAllTransactions() {
	    return transactionService.getAllTransactionsDTO();
	}
	
	@GetMapping("/transactions/{id}")
	public Transaction getTransactionById(@PathVariable int id) {
		return transactionService.getTransactionById(id);
	}
	
	@PutMapping("/transactions/{id}")
	public Transaction updateTransaction(@PathVariable int id, @RequestBody Transaction transaction) {
		return transactionService.updateTransaction(id, transaction);
	}
	
	@PostMapping("/accounts/{accountId}/deposit")
	public Transaction deposit(@PathVariable int accountId,@RequestParam double amount) {
		return transactionService.deposit(accountId, amount);
	}
	
	@PostMapping("/accounts/{accountId}/withdraw")
	public Transaction withdraw(@PathVariable int accountId,@RequestParam double amount) {
		return transactionService.withdraw(accountId, amount);
	}
	
	@PostMapping("/accounts/transfer")
	public String transfer(@RequestParam int fromAccountId,@RequestParam int toAccountId,@RequestParam double amount) {
		return transactionService.transfer(fromAccountId, toAccountId, amount);
	}
	@GetMapping("/pagination")
	public Page<Transaction> getTransactionsWithPagination(@RequestParam(defaultValue ="0") int page,@RequestParam(defaultValue ="5") int size){
		return transactionService.getTransactionsWithPagination(page, size);
	}
	
	@GetMapping("/transactions/search")
	public List<Transaction> searchTransactionsByType(@RequestParam String type){
		return transactionService.searchTransactionsByType(type);
	}
}
