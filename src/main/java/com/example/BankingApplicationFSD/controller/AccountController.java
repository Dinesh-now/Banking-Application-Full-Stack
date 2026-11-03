package com.example.BankingApplicationFSD.controller;

import java.util.List;
import com.example.BankingApplicationFSD.dto.AccountResponseDTO;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.BankingApplicationFSD.entity.Account;
import com.example.BankingApplicationFSD.service.AccountService;

@RestController
public class AccountController {
	
	private final AccountService accountService;
	
	public AccountController(AccountService accountService) {
		this.accountService=accountService;
	}
	
	@PostMapping("/accounts")
	public AccountResponseDTO createAccount(@RequestBody Account account) {
	    Account savedAccount = accountService.saveAccount(account);
	    return accountService.convertToDTO(savedAccount);
	}
	
	@GetMapping("/accounts")
	public List<Account> getAllAccounts(Authentication authentication) {
	    return accountService.getAccountsByUserEmail(
	            authentication.getName()
	    );
	}
	
	@GetMapping("/accounts/{id}")
	public AccountResponseDTO getAccountById(
	        @PathVariable int id,
	        Authentication authentication) {

	    Account account = accountService.getAccountById(
	            id,
	            authentication.getName()
	    );

	    if (account == null) {
	        return null;
	    }

	    return accountService.convertToDTO(account);
	}
	
	@PutMapping("/accounts/{id}")
	public AccountResponseDTO updateAccount(
	        @PathVariable int id,
	        @RequestBody Account account,
	        Authentication authentication) {

	    Account updatedAccount = accountService.updateAccount(
	            id,
	            account,
	            authentication.getName()
	    );

	    if (updatedAccount == null) {
	        return null;
	    }
	    
	    
	    return accountService.convertToDTO(updatedAccount);
	}
}
