package aq.project.services.unit;

import static aq.project.util.AccountLogins.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import aq.project.dto.AccountRequest;
import aq.project.entities.Account;
import aq.project.mappers.AccountMapper;
import aq.project.repositories.AccountRepository;
import aq.project.services.AccountService;
import aq.project.util.AccountServiceInitializer;

@TestInstance(Lifecycle.PER_CLASS)
class AccountServiceCreateAccountUnitTest {

	private AccountServiceInitializer accountServiceInitializer;
	
	@BeforeAll
	void initTestDependencies() {	
		accountServiceInitializer = new AccountServiceInitializer();
	}
	
	@Test
	@DisplayName("test-success-account-creation")
	void test1() {
		AccountMapper accountMapper = accountServiceInitializer.getAccountMapper();
		AccountService accountService = accountServiceInitializer.getAccountService();
		AccountRepository accountRepository = accountServiceInitializer.getAccountRepository();
		AccountRequest junkoRequest = accountServiceInitializer.getAccountRequest(JUNKO);
		Account junkoAcc = accountMapper.toAccount(junkoRequest);
		accountService.createAccount(junkoRequest);
		verify(accountRepository).save(junkoAcc);
	}
}
