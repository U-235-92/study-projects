package aq.project.services.unit;

import static aq.project.util.AccountLogins.JOHN;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import aq.project.repositories.AccountRepository;
import aq.project.services.AccountService;
import aq.project.util.AccountServiceInitializer;

@TestInstance(Lifecycle.PER_CLASS)
class AccountServiceDeleteAccountUnitTest {

	private AccountServiceInitializer accountServiceInitializer;
	
	@BeforeAll
	private void init() {
		accountServiceInitializer = new AccountServiceInitializer();
	}
	
	@Test
	@DisplayName("test-success-delete-account")
	void test1() {
		AccountRepository accountRepository = accountServiceInitializer.getAccountRepository();
		AccountService accountService = accountServiceInitializer.getAccountService();
		accountService.deleteAccount(JOHN);
		verify(accountRepository).deleteByLogin(JOHN);
	}
}
