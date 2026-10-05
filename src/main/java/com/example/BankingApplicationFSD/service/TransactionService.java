package com.example.BankingApplicationFSD.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.BankingApplicationFSD.entity.Account;
import com.example.BankingApplicationFSD.entity.Transaction;
import com.example.BankingApplicationFSD.repository.AccountRepository;
import com.example.BankingApplicationFSD.repository.TransactionRepository;

@Service
public class TransactionService {
	private final TransactionRepository transactionRepository;
	
	private final AccountRepository accountRepository;
	
	
	public TransactionService(TransactionRepository transactionRepository,AccountRepository accountRepository) {
		this.transactionRepository=transactionRepository;
		this.accountRepository=accountRepository;
	}
	
	public Transaction saveTransaction(Transaction transaction) {
		int accountId=transaction.getAccount().getId();
		
		Account account=accountRepository.findById(accountId).orElse(null);
		
		if(account!=null) {
			transaction.setAccount(account);
			return transactionRepository.save(transaction);
		}
		return null;	
	}
	
	public List<Transaction> getAllTransactions(){
		return transactionRepository.findAll();
	}
	
	public Transaction getTransactionById(int id) {
		return transactionRepository.findById(id).orElse(null);
	}
	
	public Transaction updateTransaction(int id,Transaction transaction) {
		Transaction existingTransaction=transactionRepository.findById(id).orElse(null);
		
		if(existingTransaction!=null) {
			existingTransaction.setType(transaction.getType());
			existingTransaction.setAmount(transaction.getAmount());
			existingTransaction.setDate(transaction.getDate());
			
			if(transaction.getAccount()!=null) {
				int accountId=transaction.getAccount().getId();
				Account account=accountRepository.findById(accountId).orElse(null);
				
				if(account!=null) {
					existingTransaction.setAccount(account);
				}
			}
			
			return transactionRepository.save(existingTransaction);
		}
		return null;
	}
}
