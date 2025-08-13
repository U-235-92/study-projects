package aq.project.services.it;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import aq.project.entities.Account;
import aq.project.entities.Role;
import aq.project.exceptions.AccountNotFoundException;
import aq.project.repositories.AccountRepository;
import aq.project.services.AccountService;
import aq.project.util.AccountLogins;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class AccountServiceUnblockAccountIntegrationTest {

	@Autowired
	@InjectMocks
	private AccountService accountService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@MockitoBean
	private AccountRepository accountRepository;
	
	@BeforeAll
	private void initRepo() {
		Account junkoAccount = new Account(AccountLogins.JUNKO, passwordEncoder.encode("58"), false, Role.READER);
		when(accountRepository.findByLogin(AccountLogins.JUNKO)).thenReturn(Optional.of(junkoAccount));
	}
	
	@Test
	@DisplayName("success-unblock-account-test")
	void test1() {
		accountService.blockAccount(AccountLogins.JUNKO);
		verify(accountRepository).blockAccount(AccountLogins.JUNKO);
	}
	
	@Test
	@DisplayName("fail-null-account-unblock-test")
	void test2() {
		assertThrows(NullPointerException.class, () -> accountService.blockAccount(null));
	}
	
	@Test
	@DisplayName("fail-not-found-account-unblock-test")
	void test3() {
		assertThrows(AccountNotFoundException.class, () -> accountService.blockAccount(AccountLogins.NOT_FOUND_LOGIN));
	}
}
