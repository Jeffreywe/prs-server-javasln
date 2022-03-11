package com.maxtrain.bootcamp.user;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepository userRepo;
	
	// custom methods
	@GetMapping("username/{username}/{password}")
	public ResponseEntity<User> getUserByUsernameAndPW(@PathVariable String username, @PathVariable String password) {
		var us = userRepo.findByUsernameAndPassword(username, password);
		if(us.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(us.get(), HttpStatus.OK);
	}
	
	// basic 5 methods
	@GetMapping 
	public ResponseEntity<Iterable<User>> getUsers() {
		var user = userRepo.findAll();
		return new ResponseEntity<Iterable<User>>(user, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<User> getUser(@PathVariable int id) {
		var user = userRepo.findById(id);
		if(user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<User> postUser(@RequestBody User user) {
		if(user == null || user.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var us = userRepo.save(user);
		return new ResponseEntity<User>(us, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putUser(@PathVariable int id, @RequestBody User user) {
		if(user == null || user.getId() == 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var us = userRepo.findById(user.getId());
		if(us.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userRepo.save(user);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteUser(@PathVariable int id) {
		var user = userRepo.findById(id);
		if(user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userRepo.delete(user.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
