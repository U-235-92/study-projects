package aq.project.services.it;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import aq.project.dto.AuthenticationRequest;
import aq.project.entities.Account;
import aq.project.entities.Role;
import aq.project.exceptions.AccountNotFoundException;
import aq.project.exceptions.AuthenticationRequestException;
import aq.project.exceptions.BlockedAccountException;
import aq.project.repositories.AccessTokenRepository;
import aq.project.repositories.AccountRepository;
import aq.project.services.AccessTokenService;
import aq.project.util.AccountLogins;

@SpringBootTest
class AccessTokenServiceGenerateAccessTokenIntegrationTest {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@MockitoBean
	private AccountRepository accountRepository;
	@Autowired @InjectMocks
	private AccessTokenService accessTokenService;
	@MockitoBean
	private AccessTokenRepository accessTokenRepository;
	
	@BeforeEach
	private void init() {
		Account alice = new Account("alice", passwordEncoder.encode("1"), true, Role.ADMIN);
		Account bob = new Account("bob", passwordEncoder.encode("2"), false, Role.ADMIN);
		when(accountRepository.findByLogin("alice")).thenReturn(Optional.of(alice));
		when(accountRepository.findByLogin("bob")).thenReturn(Optional.of(bob));
	}
	
	@Test
	@DisplayName("test-success-generate-access-token")
	void test1() {
		AuthenticationRequest authenticationRequest = new AuthenticationRequest("alice", "1");
		String accessToken = accessTokenService.generateAccessToken(authenticationRequest);
		assertNotNull(accessToken);
	}
	
	@Test
	@DisplayName("test-fail-null-authentication-request-generate-access-token")
	void test2() {
		Assertions.assertThrows(NullPointerException.class, () -> accessTokenService.generateAccessToken(null));
	}
	
	@Test
	@DisplayName("test-fail-wrong-authentication-request-generate-access-token")
	void test3() {
		AuthenticationRequest authenticationRequest = new AuthenticationRequest("", "1");
		Assertions.assertThrows(AuthenticationRequestException.class, () -> accessTokenService.generateAccessToken(authenticationRequest));
	}
	
	@Test
	@DisplayName("test-fail-blocked-account-generate-access-token")
	void test4() {
		AuthenticationRequest authenticationRequest = new AuthenticationRequest("bob", "2");
		Assertions.assertThrows(BlockedAccountException.class, () -> accessTokenService.generateAccessToken(authenticationRequest));
	}
	
	@Test
	@DisplayName("test-fail-incorrect-account-password-generate-access-token")
	void test5() {
		AuthenticationRequest authenticationRequest = new AuthenticationRequest("alice", "3");
		Assertions.assertThrows(BadCredentialsException.class, () -> accessTokenService.generateAccessToken(authenticationRequest));
	}
	
	@Test
	@DisplayName("test-fail-not-found-account-generate-access-token")
	void test6() {
		AuthenticationRequest authenticationRequest = new AuthenticationRequest(AccountLogins.NOT_FOUND_LOGIN, "5");
		Assertions.assertThrows(AccountNotFoundException.class, () -> accessTokenService.generateAccessToken(authenticationRequest));
	}
}
