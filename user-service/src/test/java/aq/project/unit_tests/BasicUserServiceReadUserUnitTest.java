package aq.project.unit_tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import aq.project.dto.BasicUserResponse;
import aq.project.entities.User;
import aq.project.entities.UserDetails;
import aq.project.mappers.UserToBasicUserResponseMapper;
import aq.project.repositories.UserRepository;
import aq.project.services.UserBasicService;

@ExtendWith(MockitoExtension.class)
class BasicUserServiceReadUserUnitTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private UserToBasicUserResponseMapper userToBasicUserResponseMapper;
	@InjectMocks
	private UserBasicService userBasicService;
	
	@Test
	@DisplayName("Test that read-operation is successfull")
	void luckyBasicUserReadFlowTest() {
		User alice = alice();
		given(userRepository.findByLogin(alice.getLogin())).willReturn(Optional.of(alice));
		given(userToBasicUserResponseMapper.toBasicUserResponse(alice)).willReturn(aliceBasicResponse(alice));
		when(userToBasicUserResponseMapper.toBasicUserResponse(alice)).thenReturn(aliceBasicResponse(alice));
		Authentication aliceAuthentication = getAuthentication(alice);
		assertNotNull(userBasicService.readUser(alice.getLogin(), aliceAuthentication));
	}

	private User alice() {
		UserDetails aliceDetails = new UserDetails("Alice", "K", "alice@mail.aq", LocalDate.parse("15-11-2028", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		User alice = new User("alice", "123", true, aliceDetails, List.of());
		return alice;
	}
	
	private BasicUserResponse aliceBasicResponse(User alice) {
		return new BasicUserResponse(alice.getLogin(), alice.getUserDetails().getFirstname(),
				alice.getUserDetails().getLastname(), alice.getUserDetails().getEmail(), 
				alice.getUserDetails().getBirthDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
	}
	
	@Test
	void neverCallReadUserWhenDifferentLoginPassed() {
		String differentLogin = "alice";
		User alexander = alexander();
		given(userRepository.findByLogin(alexander.getLogin())).willReturn(Optional.of(alexander));
		userBasicService.readUser(alexander.getLogin(), getAuthentication(alexander));
		verify(userRepository, never()).findByLogin(differentLogin);
	}
	
	private User alexander() {
		UserDetails alexanderDetails = new UserDetails("Alexander", "K", "alexander@mail.aq", LocalDate.parse("15-05-2030", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		User alexander = new User("alexander", "321", true, alexanderDetails, List.of());
		return alexander;
	}
	
	private Authentication getAuthentication(User user) {
		return new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword(), List.of());
	}
}
