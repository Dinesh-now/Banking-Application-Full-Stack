package com.example.BankingApplicationFSD.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BankingApplicationFSD.entity.Loan;

public interface LoanRepository  extends JpaRepository<Loan, Integer>{

}
