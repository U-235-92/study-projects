package aq.project.services.unit;

import static aq.project.util.AccountLogins.JUNKO;
import static aq.project.util.AccountLogins.NOT_FOUND_LOGIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import aq.project.dto.AccountResponse;
import aq.project.exceptions.AccountNotFoundException;
import aq.project.repositories.AccountRepository;
import aq.project.services.AccountService;
import aq.project.util.AccountServiceInitializer;

@TestInstance(Lifecycle.PER_CLASS)
class AccountServiceReadAccountUnitTest {
	
	private AccountServiceInitializer accountServiceInitializer;
	
	@BeforeAll
	void initTestDependencies() {	
		accountServiceInitializer = new AccountServiceInitializer();
	}
	
	@Test
	@DisplayName("test-success-read-account")
	void test1() {
		String login = JUNKO;
		AccountService accountService = accountServiceInitializer.getAccountService();
		AccountRepository accountRepository = accountServiceInitializer.getAccountRepository();
		AccountResponse accountResponse = accountService.readAccount(login);
		assertNotNull(accountResponse);
		assertEquals(accountResponse.getLogin(), login);
		verify(accountRepository).findByLogin(login);
	}
	
	@Test
	@DisplayName("test-fail-not-found-read-account")
	void test2() {
		AccountService accountService = accountServiceInitializer.getAccountService();
		assertThrows(AccountNotFoundException.class, () -> accountService.readAccount(NOT_FOUND_LOGIN));
	}
 }
