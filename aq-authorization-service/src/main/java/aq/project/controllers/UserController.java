package aq.project.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import aq.project.dtos.UserRequest;
import aq.project.dtos.UserResponse;
import aq.project.services.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	
	@ResponseBody
	@PostMapping("/create")
	@ResponseStatus(code = HttpStatus.CREATED)
	public String createUser(@RequestBody(required = true) UserRequest userRequest) {
		userService.createUser(userRequest);
		return String.format("User with login %s was created", userRequest.getLogin());
	}
	
	@ResponseBody
	@GetMapping("/get/{login}")
	@ResponseStatus(code = HttpStatus.OK)
	public UserResponse readUser(@PathVariable(required = true) String login) {
		return userService.readUser(login);
	}
	
	@ResponseBody
	@GetMapping("/get-all")
	@ResponseStatus(code = HttpStatus.OK)
	public List<UserResponse> readUsers() {
		return userService.readUsers();
	}
	
	@ResponseBody
	@DeleteMapping("/delete/{login}")
	@ResponseStatus(code = HttpStatus.OK)
	public String deleteUser(@PathVariable(required = true) String login) {
		userService.deleteUser(login);
		return String.format("User with login %s was deleted", login);
	}
}
