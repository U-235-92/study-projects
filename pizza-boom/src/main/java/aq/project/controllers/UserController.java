package aq.project.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aq.project.entities.User;
import aq.project.security.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	
	@GetMapping("/all")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@PutMapping("/update")
	public String updateUser(@RequestBody User user) {
		userService.updateUser(user);
		return String.format("User with id %d was updated", user.getId());
	}
}
