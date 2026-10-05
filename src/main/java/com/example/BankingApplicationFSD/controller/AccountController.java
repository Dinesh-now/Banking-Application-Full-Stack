package com.example.BankingApplicationFSD.controller;

import java.util.List;

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
	public Account createAccount(@RequestBody Account account) {
		return accountService.saveAccount(account);
	}
	
	@GetMapping("/accounts")
	public List<Account> getAllAccounts(){
		return accountService.getAllAccounts();
	}
	
	
	@GetMapping("/accounts/{id}")
	public Account getAccountById(@PathVariable int id) {
		return accountService.getAccountById(id);
	}
	
	@PutMapping("/accounts/{id}")
	public Account updateAccount(@PathVariable int id,@RequestBody Account account) {
		return accountService.updateAccount(id, account);
	}
}
