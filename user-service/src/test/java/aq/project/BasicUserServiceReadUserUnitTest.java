package aq.project;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import aq.project.dto.BasicUserResponse;
import aq.project.entities.Authority;
import aq.project.entities.User;
import aq.project.entities.UserDetails;
import aq.project.mappers.UserToBasicUserResponseMapper;
import aq.project.repositories.UserRepository;
import aq.project.services.UserBasicService;
import aq.project.utils.AuthorityNames;

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
		Authentication aliceAuthentication = aliceAuthentication();
		assertNotNull(userBasicService.readUser(alice.getLogin(), aliceAuthentication));
	}

	private User alice() {
		UserDetails aliceDetails = new UserDetails("Alice", "K", "alice@mail.aq", LocalDate.parse("15-11-2028", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		User alice = new User("alice", "123", true, aliceDetails, aliceAuthorities());
		return alice;
	}
	
	private List<Authority> aliceAuthorities() {
		Authority extendedCreateUserAuthority = new Authority(AuthorityNames.EXTENDED_CREATE_USER);
		Authority extendedReadUserAuthority = new Authority(AuthorityNames.EXTENDED_READ_USER);
		Authority extendedUpdateUserAuthority = new Authority(AuthorityNames.EXTENDED_UPDATE_USER);
		Authority extendedDeleteUserAuthority = new Authority(AuthorityNames.EXTENDED_DELETE_USER);
		Authority createAuthority = new Authority(AuthorityNames.CREATE_AUTHORITY);
		Authority readAuthority = new Authority(AuthorityNames.READ_AUTHORITY);
		Authority updateAuthority = new Authority(AuthorityNames.UPDATE_AUTHORITY);
		Authority deleteAuthority = new Authority(AuthorityNames.DELETE_AUTHORITY);
		return List.of(extendedCreateUserAuthority, extendedReadUserAuthority, extendedUpdateUserAuthority, 
				extendedDeleteUserAuthority, createAuthority, readAuthority, updateAuthority, deleteAuthority);
	}
	
	private Authentication aliceAuthentication() {
		return new UsernamePasswordAuthenticationToken("alice", "123", 
				aliceAuthorities().stream().map(auth -> new SimpleGrantedAuthority(auth.getName())).toList());
	}
	
	private BasicUserResponse aliceBasicResponse(User alice) {
		return new BasicUserResponse(alice.getLogin(), alice.getUserDetails().getFirstname(),
				alice.getUserDetails().getLastname(), alice.getUserDetails().getEmail(), 
				alice.getUserDetails().getBirthDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
	}
	
	private User alexander() {
		UserDetails alexanderDetails = new UserDetails("Alexander", "K", "alexander@mail.aq", LocalDate.parse("15-05-2030", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		User alexander = new User("alexander", "321", true, alexanderDetails, alexanderAuthorities());
		return alexander;
	}
	
	private List<Authority> alexanderAuthorities() {
		Authority basicReadUserAuthority = new Authority(AuthorityNames.BASIC_READ_USER);
		Authority basicUpdateUserAuthority = new Authority(AuthorityNames.BASIC_UPDATE_USER);
		Authority basicDeleteUserAuthority = new Authority(AuthorityNames.BASIC_DELETE_USER);
		return List.of(basicReadUserAuthority, basicUpdateUserAuthority, basicDeleteUserAuthority);
	}
	
	private Authentication alexanderAuthentication() {
		return new UsernamePasswordAuthenticationToken("alexander", "321", 
				alexanderAuthorities().stream().map(auth -> new SimpleGrantedAuthority(auth.getName())).toList());
	}
}
