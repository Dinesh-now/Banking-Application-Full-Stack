package com.example.BankingApplicationFSD.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BankingApplicationFSD.entity.Transaction;

public interface TransactionRepository  extends JpaRepository<Transaction, Integer>{
	
	List<Transaction>findByTypeIgnoreCase(String type);
	 List<Transaction> findByAccountUserEmail(String email);
	 
	 Page<Transaction> findByAccountUserEmail(String email, Pageable pageable);

}
