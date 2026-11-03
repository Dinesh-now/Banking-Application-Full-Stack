package com.example.BankingApplicationFSD.dto;

import java.time.LocalDateTime;

public class TransactionResponseDTO {

    private int id;
    private String type;
    private double amount;
    private LocalDateTime date;
    private int accountId;
    private String accountNumber;

    public TransactionResponseDTO() {
    }

    public TransactionResponseDTO(int id, String type, double amount,
            LocalDateTime date, int accountId, String accountNumber) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.accountId = accountId;
        this.accountNumber = accountNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
