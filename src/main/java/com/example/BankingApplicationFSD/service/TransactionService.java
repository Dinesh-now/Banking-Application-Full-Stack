package com.example.BankingApplicationFSD.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.BankingApplicationFSD.entity.Account;
import com.example.BankingApplicationFSD.entity.Transaction;
import com.example.BankingApplicationFSD.repository.AccountRepository;
import com.example.BankingApplicationFSD.repository.TransactionRepository;

import jakarta.transaction.Transactional;

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
		if (account == null) {
		    throw new IllegalArgumentException("Account not found");
		}

		transaction.setAccount(account);

		return transactionRepository.save(transaction);	
	}
	
	
	public Transaction deposit(int accountId,double amount) {
		
		if(amount<=0) {
			throw new IllegalArgumentException("Deposit amount must be greater than zero");
		}
		Account account=accountRepository.findById(accountId).orElse(null);
		
		if(account==null) {
			throw new IllegalArgumentException("Account not found");
		}
		account.setBalance(account.getBalance()+amount);
		accountRepository.save(account);
		
		Transaction transaction=new Transaction();
		
		transaction.setType("DEPOSIT");
		transaction.setAmount(amount);
		transaction.setDate(java.time.LocalDateTime.now());
		transaction.setAccount(account);
		return transactionRepository.save(transaction);
	}
	
	public Transaction withdraw(int accountId ,double amount) {
		
		if(amount<=0) {
			throw new IllegalArgumentException("Withdrawal amount must be greater than zero");
		}
		Account account=accountRepository.findById(accountId).orElse(null);
		
		if(account==null) {
			throw new IllegalArgumentException("Account not found");
		}
		
		if(account.getBalance() < amount) {
			throw new IllegalArgumentException("Insufficient Balance");
		}
		account.setBalance(account.getBalance()-amount);
		accountRepository.save(account);
		
		Transaction transaction =new Transaction();
		transaction.setType("WITHDRAW");
		transaction.setAmount(amount);
		transaction.setDate(java.time.LocalDateTime.now());
		transaction.setAccount(account);
		
		return transactionRepository.save(transaction);
		}
	
	@Transactional
	public String transfer(int fromAccountId,int toAccountId,double amount) {
		Account fromAccount=accountRepository.findById(fromAccountId).orElse(null);
		Account toAccount=accountRepository.findById(toAccountId).orElse(null);
		
		if(fromAccount==null || toAccount==null) {
			throw new IllegalArgumentException("Account not found");
		}
		
		if(fromAccountId==toAccountId) {
			return "Cannot transfer money to the same Account";
		}
		if(amount<=0) {
			return "Transfer Amount must be greater than Zero";
		}
		
		if(fromAccount.getBalance()<amount) {
			return "Insufficient Balance";
		}
		
		fromAccount.setBalance(fromAccount.getBalance()-amount);
		toAccount.setBalance(toAccount.getBalance()+amount);
		
		accountRepository.save(fromAccount);
		accountRepository.save(toAccount);
		
		Transaction debitTransaction=new Transaction();
		debitTransaction.setType("TRANSFER");
		debitTransaction.setAmount(amount);
		debitTransaction.setDate(java.time.LocalDateTime.now());
		debitTransaction.setAccount(fromAccount);
		
		
		Transaction creditTransaction=new Transaction();
		creditTransaction.setType("TRANSFER");
		creditTransaction.setAmount(amount);
		creditTransaction.setDate(java.time.LocalDateTime.now());
		creditTransaction.setAccount(toAccount);
		
		transactionRepository.save(debitTransaction);
		transactionRepository.save(creditTransaction);
		
		return "Transfer Successfull";
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
	
	public Page<Transaction> getTransactionsWithPagination(int page,int size){
		Pageable pageable=PageRequest.of(page, size);
		return transactionRepository.findAll(pageable);
	}
	
	public List<Transaction> searchTransactionsByType(String type){
		return transactionRepository.findByTypeIgnoreCase(type);
	}
	
}
