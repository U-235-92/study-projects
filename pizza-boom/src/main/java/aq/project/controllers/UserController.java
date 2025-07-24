package aq.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aq.project.dto.BasicUserDataDTO;
import aq.project.dto.ExtendedUserDataDTO;
import aq.project.exceptions.IncorrectUserDataException;
import aq.project.exceptions.UserIdNotFoundException;
import aq.project.exceptions.UserLoginExistsException;
import aq.project.exceptions.UserLoginNotFoundException;
import aq.project.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/basic/create_user")
	public String basicCreateUser(@RequestBody BasicUserDataDTO basicUserData) throws UserLoginExistsException, IncorrectUserDataException {
		throw new UnsupportedOperationException();
	}
	
	@GetMapping("/basic/read_user_by_id/{id}")
	public BasicUserDataDTO basicReadUserDataById(@PathVariable(required = true) int id, Authentication authentication) throws UserIdNotFoundException {
		throw new UnsupportedOperationException();
	}
	
	@GetMapping("/basic/read_user_by_login/{login}")
	public BasicUserDataDTO basicReadUserDataByLogin(@PathVariable(required = true) String login, Authentication authentication) throws UserLoginNotFoundException {
		throw new UnsupportedOperationException();
	}
	
	@PatchMapping("/basic/update_user_by_id/{id}")
	public String basicUpdateUserById(@PathVariable(required = true) int id, @RequestBody BasicUserDataDTO basicUserData, Authentication authentication) throws UserIdNotFoundException, UserLoginExistsException, IncorrectUserDataException {
		throw new UnsupportedOperationException();
	}
	
	@DeleteMapping("/basic/delete_user_by_id/{id}")
	public String basicDeleteUserById(@PathVariable(required = true) int id, Authentication authentication) throws UserIdNotFoundException {
		throw new UnsupportedOperationException();
	}
	
	@PostMapping("/extended/create_user")
	public String extendedCreateUser(@RequestBody ExtendedUserDataDTO extendedUserData) throws UserLoginExistsException, IncorrectUserDataException {
		throw new UnsupportedOperationException();
	}
	
	@PatchMapping("/extended/update_user_by_id/{id}")
	public String extendedUpdateUserById(@PathVariable(required = true) int id, @RequestBody ExtendedUserDataDTO extendedUserData) throws UserIdNotFoundException, UserLoginExistsException, IncorrectUserDataException {
		throw new UnsupportedOperationException();
	}
	
	@DeleteMapping("/extended/delete_user_by_id/{id}")
	public String extendedDeleteUserById(@PathVariable(required = true) int id) throws UserIdNotFoundException {
		throw new UnsupportedOperationException();
	}
	
	@GetMapping("/extended/read_user_by_id/{id}")
	public ExtendedUserDataDTO extendedReadUserById(@PathVariable(required = true) int id) throws UserIdNotFoundException {
		throw new UnsupportedOperationException();
	}
	
	@GetMapping("/extended/read_user_by_login/{login}")
	public ExtendedUserDataDTO extendedReadUserByLogin(@PathVariable(required = true) String login) throws UserLoginNotFoundException {
		throw new UnsupportedOperationException();
	}
	
	@GetMapping("/extended/read_all_users")
	public List<ExtendedUserDataDTO> extendedReadAllUsers() {
		throw new UnsupportedOperationException();
	}
}
