package com.example.BankingApplicationFSD.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.BankingApplicationFSD.entity.Loan;
import com.example.BankingApplicationFSD.entity.User;
import com.example.BankingApplicationFSD.repository.LoanRepository;
import com.example.BankingApplicationFSD.repository.UserRepository;

@Service
public class LoanService {
	
	private final LoanRepository loanRepository;
	private final UserRepository userRepository;
	
	public LoanService(LoanRepository loanRepository ,UserRepository userRepository) {
		this.loanRepository=loanRepository;
		this.userRepository=userRepository;
	}
	
	public Loan saveLoan(Loan loan) {
		int userId=loan.getUser().getId();
		User user=userRepository.findById(userId).orElse(null);
		
		if(user!=null) {
			loan.setUser(user);
			return loanRepository.save(loan);
		}
		return null;
	}
	
	
	public List<Loan> getAllLoans(){
		return loanRepository.findAll();
	}
	
	
	public Loan getLoanById(int id) {
		return loanRepository.findById(id).orElse(null);
	}
	
	public Loan updateLoan(int id,Loan loan) {
		
		Loan existingLoan=loanRepository.findById(id).orElse(null);
		
		if(existingLoan!=null) {
			existingLoan.setAmount(loan.getAmount());
			existingLoan.setInterestRate(loan.getInterestRate());
			existingLoan.setStatus(loan.getStatus());
			
			if(loan.getUser()!=null) {
				int userId=loan.getUser().getId();
				
				User user=userRepository.findById(userId).orElse(null);
				if(user!=null) {
					existingLoan.setUser(user);
				}
			}
			return loanRepository.save(existingLoan);
			
		}
		return null;
	}
}
