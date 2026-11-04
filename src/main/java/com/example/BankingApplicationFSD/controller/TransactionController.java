package com.example.BankingApplicationFSD.controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Authentication;
import java.util.stream.Collectors;
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
	public Transaction createTransaction(
	        @RequestBody Transaction transaction,
	        Authentication authentication) {

	    return transactionService.saveTransaction(
	            transaction, authentication.getName());
	}
	
	@GetMapping("/transactions")
	public List<TransactionResponseDTO> getAllTransactions(
	        Authentication authentication) {

	    return transactionService
	            .getTransactionsByUserEmail(authentication.getName())
	            .stream()
	            .map(transactionService::convertToDTO)
	            .collect(Collectors.toList());
	}
	
	@GetMapping("/transactions/{id}")
	public TransactionResponseDTO getTransactionById(
	        @PathVariable int id,
	        Authentication authentication) {

	    Transaction transaction = transactionService.getTransactionById(
	            id, authentication.getName());

	    return transactionService.convertToDTO(transaction);
	}
	@PutMapping("/transactions/{id}")
	public Transaction updateTransaction(
	        @PathVariable int id,
	        @RequestBody Transaction transaction,
	        Authentication authentication) {

	    return transactionService.updateTransaction(
	            id, transaction, authentication.getName());
	}
	@PostMapping("/accounts/{accountId}/deposit")
	public Transaction deposit(
	        @PathVariable int accountId,
	        @RequestParam double amount,
	        Authentication authentication) {

	    return transactionService.deposit(
	            accountId, amount, authentication.getName());
	}
	
	@PostMapping("/accounts/{accountId}/withdraw")
	public Transaction withdraw(
	        @PathVariable int accountId,
	        @RequestParam double amount,
	        Authentication authentication) {

	    return transactionService.withdraw(
	            accountId, amount, authentication.getName());
	}
	
	@PostMapping("/accounts/transfer")
	public String transfer(
	        @RequestParam int fromAccountId,
	        @RequestParam int toAccountId,
	        @RequestParam double amount,
	        Authentication authentication) {

	    return transactionService.transfer(
	            fromAccountId,
	            toAccountId,
	            amount,
	            authentication.getName());
	}
	
	@GetMapping("/pagination")
	public Page<Transaction> getTransactionsWithPagination(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size,
	        Authentication authentication) {

	    return transactionService.getTransactionsWithPagination(
	            page, size, authentication.getName());
	}
	
	@GetMapping("/transactions/search")
	public List<Transaction> searchTransactionsByType(
	        @RequestParam String type,
	        Authentication authentication) {

	    return transactionService.searchTransactionsByType(
	            type, authentication.getName());
	}
}
