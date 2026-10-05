package com.example.BankingApplicationFSD.service;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BankingApplicationFSD.entity.User;
import com.example.BankingApplicationFSD.repository.UserRepository;

@Service
public class UserService {
	

	private  final  UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	public User saveUser(User user) {
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
		
		if(existingUser!=null) {
			existingUser.setName(user.getName());
			existingUser.setEmail(user.getEmail());
			existingUser.setPassword(user.getPassword());
			
			return userRepository.save(existingUser);
		}
		return null;
	}
	
	public void deleteUser(int id) {
		userRepository.deleteById(id);
	}
}
