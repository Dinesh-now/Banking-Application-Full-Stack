package com.example.BankingApplicationFSD.dto;

public class AccountResponseDTO {
	
	private int id;
	private String accountNumber;
	private double balance;
	
	private int userId;
	private String userName;
	private String userEmail;
	
	public AccountResponseDTO() {}
	
	public AccountResponseDTO(int id,String accountNumber,double balance,int userId,String userName,String userEmail) {
		this.id=id;
		this.accountNumber=accountNumber;
		this.balance=balance;
		
		this.userId=userId;
		this.userName=userName;
		this.userEmail=userEmail;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}	
	
	
}
