package com.example.BankingApplicationFSD.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.BankingApplicationFSD.entity.Account;
import com.example.BankingApplicationFSD.entity.User;
import com.example.BankingApplicationFSD.repository.AccountRepository;
import com.example.BankingApplicationFSD.repository.UserRepository;

@Service
public class AccountService {
	
	private final AccountRepository accountRepository;
	private final UserRepository userRepository;
	
	public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
		this.accountRepository=accountRepository;
		this.userRepository=userRepository;
	}
	
	public Account saveAccount(Account account) {
		int userId=account.getUser().getId();
		
		User user=userRepository.findById(userId).orElse(null);
		if(user!=null) {
			account.setUser(user);
		    return accountRepository.save(account);
		}
		return null;
	}
	
	public List<Account> getAllAccounts(){
		return accountRepository.findAll();
	}
	
	
	public Account getAccountById(int id) {
		return accountRepository.findById(id).orElse(null);
	}
	
	public Account updateAccount(int id,Account account) {
		Account existingAccount=accountRepository.findById(id).orElse(null);
		
		if(existingAccount!=null) {
			existingAccount.setAccountNumber(account.getAccountNumber());
			existingAccount.setBalance(account.getBalance());
			
			if(account.getUser()!=null) {
				int userId=account.getUser().getId();
				User user=userRepository.findById(userId).orElse(null);
				
				if(user!=null) {
					existingAccount.setUser(user);
				}
			}
			return accountRepository.save(existingAccount);
		}
		return null;
		
	}
	
}
