package com.spring.boot.micro.services.user;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/user")
class UserApi {

	@Autowired
	private UserDaoImpl userService;

	@GetMapping(path = "/all")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping(path = "/find/{id}")
	public User getUser(@PathVariable int id) {
		return userService.getUser(id);
	}

	@PostMapping("/add")
	public ResponseEntity<Object> addUser(@Valid @RequestBody User user) throws MalformedURLException {
		userService.addUser(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId())
				.toUri();
		System.out.println("location : " + location.toURL());

		return ResponseEntity.created(location).build();
	}

	@DeleteMapping(path = "/remove/{id}")
	public ResponseEntity<Object> removeUser(@PathVariable int id) throws MalformedURLException {
		userService.removeUser(id);
		return ResponseEntity.ok("User: " + id + " removed.");
	}
}
