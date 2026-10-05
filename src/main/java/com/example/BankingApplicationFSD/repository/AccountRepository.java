package com.example.BankingApplicationFSD.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BankingApplicationFSD.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

	
}
