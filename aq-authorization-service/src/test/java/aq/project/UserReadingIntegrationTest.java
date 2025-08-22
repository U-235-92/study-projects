package aq.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import aq.project.dtos.UserResponse;
import aq.project.entities.Role;
import aq.project.entities.User;
import aq.project.exceptions.UserNotFoundException;
import aq.project.repositories.UserRepository;
import aq.project.services.UserService;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class UserReadingIntegrationTest {

	@Autowired
	private UserService userService;
	@Autowired 
	private UserRepository userRepository;
	
	@Test
	@DisplayName("successUserReadingTest")
	void test1() {
		User user = new User("abc", "abc", "abc@mail.com", Role.ADMIN);
		userRepository.save(user);
		UserResponse userResponse = userService.readUser("abc");
		Assertions.assertNotNull(userResponse);
	}
	
	@Test
	@DisplayName("failUserReadingWithNullParameterTest")
	void test2() {
		Assertions.assertThrows(ConstraintViolationException.class, () -> userService.readUser(null));
	}
	
	@Test
	@DisplayName("failUserReadingWithBlankParameterTest")
	void test3() {
		Assertions.assertThrows(ConstraintViolationException.class, () -> userService.readUser(""));
	}
	
	@Test
	@DisplayName("failUserReadingUnknownUserTest")
	void test4() {
		Assertions.assertThrows(UserNotFoundException.class, () -> userService.readUser("john"));
	}
}
