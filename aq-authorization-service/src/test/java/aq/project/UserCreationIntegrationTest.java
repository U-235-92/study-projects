package aq.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import aq.project.dtos.UserRequest;
import aq.project.entities.Role;
import aq.project.entities.User;
import aq.project.exceptions.UserAlreadyExistException;
import aq.project.repositories.UserRepository;
import aq.project.services.UserService;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class UserCreationIntegrationTest {

	@Autowired
	private UserService userService;
	@Autowired 
	private UserRepository userRepository;
	
	@Test
	@DisplayName("successUserCreationTest")
	void test1() {
		UserRequest userRequest = new UserRequest("test", "test", "test@mail.com", Role.ADMIN.name());
		userService.createUser(userRequest);
		Assertions.assertTrue(userRepository.findByLogin("test").isPresent());
	}
	
	@Test
	@DisplayName("failUserCreationWithWrongUserParametersTest")
	void test2() {
		UserRequest userRequest = new UserRequest("", null, "test@mail.com", Role.ADMIN.name());
		Assertions.assertThrows(ConstraintViolationException.class, () -> userService.createUser(userRequest));
	}
	
	@Test
	@DisplayName("failUserCreationWithNullParameterTest")
	void test3() {
		Assertions.assertThrows(ConstraintViolationException.class, () -> userService.createUser(null));
	}
	
	@Test
	@DisplayName("failUserCreationWithExistUserTest")
	void test4() {
		UserRequest userRequest = new UserRequest("exist-user", "test", "exist@mail.com", Role.ADMIN.name());
		User user = new User("exist-user", "test", "exist@mail.com", Role.ADMIN);
		userRepository.save(user);
		Assertions.assertThrows(UserAlreadyExistException.class, () -> userService.createUser(userRequest));
	}
}
