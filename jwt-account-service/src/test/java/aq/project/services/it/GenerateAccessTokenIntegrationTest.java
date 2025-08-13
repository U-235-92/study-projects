package aq.project.services.it;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import aq.project.dto.AuthenticationRequest;
import aq.project.entities.Account;
import aq.project.entities.Role;
import aq.project.repositories.AccessTokenRepository;
import aq.project.repositories.AccountRepository;
import aq.project.services.AccessTokenService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class GenerateAccessTokenIntegrationTest {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@MockitoBean
	private AccountRepository accountRepository;
	@Autowired
	private AccessTokenService accessTokenService;
	@MockitoBean
	private AccessTokenRepository accessTokenRepository;
	
	@BeforeAll
	private void init() {
		Account alice = new Account("alice", passwordEncoder.encode("1"), true, Role.ADMIN);
		when(accountRepository.findByLogin(Mockito.anyString())).thenReturn(Optional.of(alice));
	}
	
	@Test
	@DisplayName("test-generate-access-token")
	void test1() {
		AuthenticationRequest authenticationRequest = new AuthenticationRequest("alice", "1");
		String accessToken = accessTokenService.generateAccessToken(authenticationRequest);
		assertNotNull(accessToken);
	}
}
