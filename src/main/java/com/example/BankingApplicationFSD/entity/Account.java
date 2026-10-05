package com.example.BankingApplicationFSD.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String accountNumber;
	private double balance;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	public Account() {}
	
	public Account(String accountNumber, double balance) {
		this.accountNumber=accountNumber;
		this.balance=balance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user=user;
	}
	
	
}
