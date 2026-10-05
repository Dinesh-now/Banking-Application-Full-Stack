package com.example.BankingApplicationFSD.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.BankingApplicationFSD.entity.User;
import com.example.BankingApplicationFSD.service.UserService;

@RestController
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@PostMapping("/users")
	public User createUser(@RequestBody User user) {
		return userService.saveUser(user);
	}
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@GetMapping("/users/{id}")
	public User getUserById(@PathVariable int id) {
		return userService.getUserById(id);
	}
	@PutMapping("/users/{id}")
	public User updateUser(@PathVariable int id,@RequestBody User user) {
		return userService.updateUser(id, user);
	}
	
	@DeleteMapping("/users/{id}")
	public String deleteUser( @PathVariable int id) {
		 userService.deleteUser(id);
		 return "user deleted succesfully";
	}
}
