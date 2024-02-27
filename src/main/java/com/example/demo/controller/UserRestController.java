package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.UserRepository;

import jakarta.annotation.security.RolesAllowed;

import com.example.demo.model.User;

@RestController
@RequestMapping("/users")
public class UserRestController {

	@Autowired
	private UserRepository usersRepository;
	
	@GetMapping("/home")
	public String home() {
		return "Welcome Home page";
	}
	
	//@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/")
	public List<User> getUsers() {
		return usersRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public User getUser(@PathVariable Long id) {
		return usersRepository.findById(id).orElse(null);
	}
	
	//@Secured("ROLE_ADMIN")
	@PostMapping("/")
	public User addPerson(@RequestBody User person) {
		return usersRepository.save(person);
	}
	
	//@PreAuthorize("hasRole('ROLE_USER') and hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public void deletePerson(@PathVariable Long id) {
		usersRepository.deleteById(id);
	}
}
