package com.example.BankingApplicationFSD.service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import com.example.BankingApplicationFSD.dto.TransactionResponseDTO;

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
	
	public TransactionResponseDTO convertToDTO(Transaction transaction) {
	    return new TransactionResponseDTO(
	        transaction.getId(),
	        transaction.getType(),
	        transaction.getAmount(),
	        transaction.getDate(),
	        transaction.getAccount().getId(),
	        transaction.getAccount().getAccountNumber()
	    );
	}
	
	public Transaction saveTransaction(Transaction transaction, String email) {

	    int accountId = transaction.getAccount().getId();

	    Account account = accountRepository.findById(accountId)
	            .orElseThrow(() -> new ResponseStatusException(
	                    HttpStatus.NOT_FOUND, "Account not found"));

	    if (!account.getUser().getEmail().equals(email)) {
	        throw new ResponseStatusException(
	                HttpStatus.FORBIDDEN, "You cannot access this account");
	    }

	    transaction.setAccount(account);

	    return transactionRepository.save(transaction);
	}
	
	
	public Transaction deposit(int accountId, double amount, String email) {

	    if (amount <= 0) {
	        throw new IllegalArgumentException(
	                "Deposit amount must be greater than zero");
	    }

	    Account account = accountRepository.findById(accountId).orElse(null);

	    if (account == null) {
	        throw new ResponseStatusException(
	                HttpStatus.NOT_FOUND, "Account not found");
	    }

	    if (!account.getUser().getEmail().equals(email)) {
	        throw new ResponseStatusException(
	                HttpStatus.FORBIDDEN, "You cannot access this account");
	    }

	    account.setBalance(account.getBalance() + amount);
	    accountRepository.save(account);

	    Transaction transaction = new Transaction();
	    transaction.setType("DEPOSIT");
	    transaction.setAmount(amount);
	    transaction.setDate(java.time.LocalDateTime.now());
	    transaction.setAccount(account);

	    return transactionRepository.save(transaction);
	}
	public Transaction withdraw(int accountId, double amount, String email) {

	    if (amount <= 0) {
	        throw new IllegalArgumentException(
	                "Withdrawal amount must be greater than zero");
	    }

	    Account account = accountRepository.findById(accountId).orElse(null);

	    if (account == null) {
	        throw new ResponseStatusException(
	                HttpStatus.NOT_FOUND, "Account not found");
	    }

	    if (!account.getUser().getEmail().equals(email)) {
	        throw new ResponseStatusException(
	                HttpStatus.FORBIDDEN, "You cannot access this account");
	    }

	    if (account.getBalance() < amount) {
	        throw new IllegalArgumentException("Insufficient Balance");
	    }

	    account.setBalance(account.getBalance() - amount);
	    accountRepository.save(account);

	    Transaction transaction = new Transaction();
	    transaction.setType("WITHDRAW");
	    transaction.setAmount(amount);
	    transaction.setDate(java.time.LocalDateTime.now());
	    transaction.setAccount(account);

	    return transactionRepository.save(transaction);
	}
	
	@Transactional
	public String transfer(int fromAccountId, int toAccountId,
	        double amount, String email) {

	    Account fromAccount = accountRepository.findById(fromAccountId)
	            .orElseThrow(() -> new ResponseStatusException(
	                    HttpStatus.NOT_FOUND, "Source account not found"));

	    Account toAccount = accountRepository.findById(toAccountId)
	            .orElseThrow(() -> new ResponseStatusException(
	                    HttpStatus.NOT_FOUND, "Destination account not found"));

	    // Check source account ownership
	    if (!fromAccount.getUser().getEmail().equals(email)) {
	        throw new ResponseStatusException(
	                HttpStatus.FORBIDDEN, "You cannot access this account");
	    }

	    if (fromAccountId == toAccountId) {
	        throw new IllegalArgumentException(
	                "Cannot transfer money to the same account");
	    }

	    if (amount <= 0) {
	        throw new IllegalArgumentException(
	                "Transfer amount must be greater than zero");
	    }

	    if (fromAccount.getBalance() < amount) {
	        throw new IllegalArgumentException("Insufficient Balance");
	    }

	    fromAccount.setBalance(fromAccount.getBalance() - amount);
	    toAccount.setBalance(toAccount.getBalance() + amount);

	    accountRepository.save(fromAccount);
	    accountRepository.save(toAccount);

	    Transaction debitTransaction = new Transaction();
	    debitTransaction.setType("TRANSFER");
	    debitTransaction.setAmount(amount);
	    debitTransaction.setDate(java.time.LocalDateTime.now());
	    debitTransaction.setAccount(fromAccount);

	    Transaction creditTransaction = new Transaction();
	    creditTransaction.setType("TRANSFER");
	    creditTransaction.setAmount(amount);
	    creditTransaction.setDate(java.time.LocalDateTime.now());
	    creditTransaction.setAccount(toAccount);

	    transactionRepository.save(debitTransaction);
	    transactionRepository.save(creditTransaction);

	    return "Transfer Successful";
	}
	
	public List<Transaction> getAllTransactions() {
	    return transactionRepository.findAll();
	}
	
	public List<Transaction> getTransactionsByUserEmail(String email) {
	    return transactionRepository.findByAccountUserEmail(email);
	}
	
	
	public List<TransactionResponseDTO> getAllTransactionsDTO() {
	    return transactionRepository.findAll()
	            .stream()
	            .map(this::convertToDTO)
	            .toList();
	}
	
	
	public Transaction getTransactionById(int id, String email) {

	    Transaction transaction = transactionRepository.findById(id)
	            .orElseThrow(() -> new ResponseStatusException(
	                    HttpStatus.NOT_FOUND, "Transaction not found"));

	    if (!transaction.getAccount().getUser().getEmail().equals(email)) {
	        throw new ResponseStatusException(
	                HttpStatus.FORBIDDEN, "You cannot access this transaction");
	    }

	    return transaction;
	}
	
	public Transaction updateTransaction(int id, Transaction transaction, String email) {

	    Transaction existingTransaction = transactionRepository.findById(id)
	            .orElseThrow(() -> new ResponseStatusException(
	                    HttpStatus.NOT_FOUND, "Transaction not found"));

	   
	    if (!existingTransaction.getAccount().getUser().getEmail().equals(email)) {
	        throw new ResponseStatusException(
	                HttpStatus.FORBIDDEN, "You cannot update this transaction");
	    }

	    existingTransaction.setType(transaction.getType());
	    existingTransaction.setAmount(transaction.getAmount());
	    existingTransaction.setDate(transaction.getDate());

	   
	    return transactionRepository.save(existingTransaction);
	}
	
	public Page<Transaction> getTransactionsWithPagination(
	        int page, int size, String email) {

	    Pageable pageable = PageRequest.of(page, size);

	    return transactionRepository.findByAccountUserEmail(email, pageable);
	}
	
	public List<Transaction> searchTransactionsByType(String type, String email) {

	    return transactionRepository.findByAccountUserEmail(email)
	            .stream()
	            .filter(transaction ->
	                    transaction.getType().equalsIgnoreCase(type))
	            .toList();
	}
	
}
