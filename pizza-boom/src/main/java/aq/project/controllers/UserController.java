package aq.project.controllers;

import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aq.project.entities.User;
import aq.project.exceptions.UserIdNotFoundException;
import aq.project.exceptions.UserLoginNotFoundException;
import aq.project.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	@Qualifier(value = "infoMessages")
	private Properties properties;
	
	@GetMapping("/all")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/id/{id}")
	public User getUserById(@PathVariable(value = "id", required = true) int id) throws UserIdNotFoundException {
		User user = userService.getUserById(id);
		return user;
	}
	
	@GetMapping("/login/{login}")
	public User getUserByLogin(@PathVariable(value = "login", required = true) String login) throws UserLoginNotFoundException {
		User user = userService.getUserByLogin(login);
		return user;
	}
	
	@PutMapping("/update")
	public String updateUser(@RequestBody User user) throws UserLoginNotFoundException {
		userService.updateUser(user);
		String msg = MessageFormat.format(properties.getProperty("info.user.updated"), user.getId());
		return msg;
	}
	
	@PatchMapping("/update/password/{login}")
	public String changePassword(@PathVariable(value = "login", required = true) String login, @RequestBody String password) {
		userService.changePassword(login, password);
		String msg = MessageFormat.format(properties.getProperty("info.user.password-changed"), login);
		return msg;
	}
	
	@DeleteMapping("/delete/{login}")
	public String deleteUserByLogin(@PathVariable(value = "login", required = true) String login) throws UserLoginNotFoundException {
		userService.deleteUser(login);
		String msg = MessageFormat.format(properties.getProperty("info.user.deleted"), login);
		return msg;
	}
}
