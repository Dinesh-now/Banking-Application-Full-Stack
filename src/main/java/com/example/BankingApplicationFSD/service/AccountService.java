package com.example.BankingApplicationFSD.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.stream.Collectors;

import com.example.BankingApplicationFSD.dto.AccountResponseDTO;

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
	
	public AccountResponseDTO convertToDTO(Account account) {
	    return new AccountResponseDTO(
	        account.getId(),
	        account.getAccountNumber(),
	        account.getBalance(),
	        account.getUser().getId(),
	        account.getUser().getName(),
	        account.getUser().getEmail()
	    );
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
	
	public List<Account> getAccountsByUserEmail(String email){
		return accountRepository.findByUserEmail(email);
		}
	
	
	public Account getAccountById(int id, String email) {

	    Account account = accountRepository.findById(id)
	            .orElseThrow(() -> new ResponseStatusException(
	                    HttpStatus.NOT_FOUND, "Account not found"));

	    if (!account.getUser().getEmail().equals(email)) {
	        throw new ResponseStatusException(
	                HttpStatus.FORBIDDEN, "You cannot access this account");
	    }

	    return account;
	}
	
	public Account updateAccount(int id, Account account, String email) {

	    Account existingAccount =
	            accountRepository.findById(id).orElse(null);

	    if (existingAccount == null) {
	        return null;
	    }

	  
	    if (!existingAccount.getUser().getEmail().equals(email)) {
	        return null;
	    }

	    existingAccount.setAccountNumber(account.getAccountNumber());
	    existingAccount.setBalance(account.getBalance());

	 

	    return accountRepository.save(existingAccount);
	}
	
	public List<AccountResponseDTO> getAllAccountsDTO() {
	    return accountRepository.findAll()
	            .stream()
	            .map(this::convertToDTO)
	            .collect(Collectors.toList());
	}
	
}
