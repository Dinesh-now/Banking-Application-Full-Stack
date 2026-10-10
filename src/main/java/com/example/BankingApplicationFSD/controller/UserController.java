package com.example.BankingApplicationFSD.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.BankingApplicationFSD.dto.UserResponseDTO;
import com.example.BankingApplicationFSD.entity.User;
import com.example.BankingApplicationFSD.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@PostMapping("/users")
	public UserResponseDTO createUser( @Valid @RequestBody User user) {
		User savedUser=userService.saveUser(user);
		return userService.convertToDTO(savedUser);
	}
	
	
	@GetMapping("/users")
	public List<UserResponseDTO> getAllUsers(){
		return userService.getAllUsers()
				.stream()
				.map(userService::convertToDTO)
				.collect(Collectors.toList());
	}
	
	@GetMapping("/users/{id}")
	public UserResponseDTO getUserById(@PathVariable int id) {
		User user=userService.getUserById(id);
		return userService.convertToDTO(user);
	}
	@PutMapping("/users/{id}")
	public UserResponseDTO updateUser(@PathVariable int id,@Valid @RequestBody User user) {
		User updatedUser=userService.updateUser(id, user);
		return userService.convertToDTO(updatedUser);
	}
	
	@DeleteMapping("/users/{id}")
	public String deleteUser( @PathVariable int id) {
		 userService.deleteUser(id);
		 return "user deleted succesfully";
	}
}
