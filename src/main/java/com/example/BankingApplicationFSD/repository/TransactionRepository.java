package com.example.BankingApplicationFSD.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BankingApplicationFSD.entity.Transaction;

public interface TransactionRepository  extends JpaRepository<Transaction, Integer>{
	
	List<Transaction>findByTypeIgnoreCase(String type);

}
