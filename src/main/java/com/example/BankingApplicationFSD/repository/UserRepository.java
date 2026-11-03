package com.example.BankingApplicationFSD.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.BankingApplicationFSD.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
	
	Optional<User>findByEmail(String email);
}
