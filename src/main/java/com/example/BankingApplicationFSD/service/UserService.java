package com.example.BankingApplicationFSD.service;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.BankingApplicationFSD.dto.UserResponseDTO;
import com.example.BankingApplicationFSD.entity.User;
import com.example.BankingApplicationFSD.repository.UserRepository;

@Service
public class UserService {
	

	private  final  UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
		this.userRepository=userRepository;
		this.passwordEncoder=passwordEncoder;
	}
	
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	
	public User getUserById(int id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public User updateUser(int id ,User user) {
		User existingUser=userRepository.findById(id).orElse(null);
		

	    if (existingUser != null) {

	        existingUser.setName(user.getName());
	        existingUser.setEmail(user.getEmail());

	        if (user.getPassword() != null && !user.getPassword().isBlank()) {
	            existingUser.setPassword(
	                passwordEncoder.encode(user.getPassword())
	            );
	        }

	        return userRepository.save(existingUser);
	    }
		return null;
	}
	
	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}
	
	public UserResponseDTO convertToDTO(User user) {
		return new UserResponseDTO(
				user.getId(),
				user.getName(),
				user.getEmail()
				);
	}
}
