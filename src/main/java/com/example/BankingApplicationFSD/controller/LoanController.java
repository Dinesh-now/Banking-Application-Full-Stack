package com.example.BankingApplicationFSD.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.BankingApplicationFSD.entity.Loan;
import com.example.BankingApplicationFSD.service.LoanService;

@RestController
public class LoanController {
	
	private final LoanService loanService;
	
	public LoanController(LoanService loanService) {
		this.loanService=loanService;
	}
	
	@PostMapping("/loans")
	public Loan createLoan(@RequestBody Loan loan) {
		return loanService.saveLoan(loan);
	}
	
	@GetMapping("/loans")
	public List<Loan> getAllLoans(){
		return loanService.getAllLoans();
	}
	
	@GetMapping("/loans/{id}")
	public Loan getLoanById(@PathVariable int id) {
		return loanService.getLoanById(id);
	}
	
	@PutMapping("loans/{id}")
	public Loan updateLoan(@PathVariable int id,@RequestBody Loan loan) {
		return loanService.updateLoan(id, loan);
	}
	
}
