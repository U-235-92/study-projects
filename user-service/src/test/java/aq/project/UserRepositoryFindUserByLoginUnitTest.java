package aq.project;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import aq.project.entities.Authority;
import aq.project.entities.User;
import aq.project.entities.UserDetails;
import aq.project.repositories.UserRepository;
import aq.project.utils.AuthorityNames;

class UserRepositoryFindUserByLoginUnitTest {
	
	@Test
	void findUserByLoginUserExist() {
		UserRepository userRepository = mock(UserRepository.class);
		given(userRepository.findByLogin("alice")).willReturn(Optional.of(alice()));
		assertNotNull(userRepository.findByLogin("alice").get());
		verify(userRepository, never()).findByLogin("john");
	}
	
	@Test
	void findUserByLoginUserDoesNotExist() {
		UserRepository userRepository = mock(UserRepository.class);
		given(userRepository.findByLogin(anyString())).willReturn(Optional.empty());
		assertThrows(NoSuchElementException.class, () -> userRepository.findByLogin("john").get());
	}
	
	private User alice() {
		UserDetails aliceDetails = new UserDetails("Alice", "K", "alice@mail.aq", LocalDate.parse("15-11-2028", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		User alice = new User("alice", "123", true, aliceDetails, extendedAuthorities());
		return alice;
	}
	
	private List<Authority> extendedAuthorities() {
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
}
