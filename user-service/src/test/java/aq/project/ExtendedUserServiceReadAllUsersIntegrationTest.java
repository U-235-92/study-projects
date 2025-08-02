package aq.project;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import aq.project.mappers.UserToExtendedUserResponseMapper;
import aq.project.repositories.UserRepository;
import aq.project.services.UserExtendedService;

@SpringBootTest
class ExtendedUserServiceReadAllUsersIntegrationTest {

	@Autowired
	private UserExtendedService userExtendedService;
	@MockitoBean
	private UserRepository userRepository;
	@MockitoBean
	private UserToExtendedUserResponseMapper userExtendedUserResponseMapper;
	
	@Test
	@DisplayName("Check the communication of UserExtendedService with its dependencies: UserRepository and UserToExtendedUserResponseMapper")
	void readAllUsersIntegrationTest() {
		assertNotNull(userExtendedService.readAllUsers());
		verify(userRepository).findAll();
	}

}
