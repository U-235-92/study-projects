package aq.project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import aq.project.entities.Role;
import aq.project.entities.User;
import aq.project.exceptions.UserNotFoundException;
import aq.project.repositories.UserRepository;
import aq.project.services.UserService;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class UserDeletionIntegrationTest {

	@Autowired
	private UserService userService;
	@Autowired 
	private UserRepository userRepository;
	
	@Test
	@DisplayName("successUserDeleteTest")
	void test1() {
		User user = new User("mew", "mew", "mew@mail.com", Role.USER);
		userRepository.save(user);
		userService.deleteUser("mew");
		Assertions.assertThrows(UserNotFoundException.class, () -> userService.deleteUser("mew"));
	}
	
	@Test
	@DisplayName("faliUnknownUserDeleteTest")
	void test2() {
		Assertions.assertThrows(UserNotFoundException.class, () -> userService.deleteUser("duck"));
	}
	
	@Test
	@DisplayName("faliUserDeleteWithNullParameterTest")
	void test3() {
		Assertions.assertThrows(ConstraintViolationException.class, () -> userService.deleteUser(null));
	}
	
	@Test
	@DisplayName("faliUserDeleteWithBlankParameterTest")
	void test4() {
		Assertions.assertThrows(ConstraintViolationException.class, () -> userService.deleteUser(""));
	}
}
